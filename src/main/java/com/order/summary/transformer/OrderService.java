package com.order.summary.transformer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderService implements IOrderService {
    private final IOrderSummaryProvider orderSummaryProvider;

    @Override
    public CustomerOrderView GetCustomerOrderView() {
        log.info("START : GetCustomerOrderView");

        List<OrderSummary> orderSummary;
        try {
            orderSummary = orderSummaryProvider.getOrderSummary();
        } catch (IOException e) {
            log.error("Unable to fetch order summary.", e);

            throw new RuntimeException(); //TODO Implement Domain Exception to get the better view of the errors.
        }

        var customers = new ArrayList<Customer>();
        var orders = new ArrayList<Order>();

        orderSummary.forEach(x -> {
            customers.add(getCustomer(x));
            orders.add(getOrder(x));
        });

        log.info("END : GetCustomerOrderView");
        return CustomerOrderView.builder()
                .customers(customers)
                .orders(orders)
                .build();
    }

    private Order getOrder(OrderSummary orderSummary) {
        var orders = orderSummary.getOrder().entrySet().stream().map(co -> {
            var productName = co.getKey();
            var quantity = co.getValue().getQuantity();
            var price = co.getValue().getPrice();
            var revenue = quantity * price;

            return Order.OrderDetail.builder()
                    .item(productName)
                    .revenue(revenue)
                    .price(price)
                    .quantity(quantity)
                    .build();
        }).collect(Collectors.toList());

        return Order
                .builder()
                .id(orderSummary.getId())
                .vendor(orderSummary.getVendor())
                .date(orderSummary.getDate())
                .customerId(orderSummary.getCustomer().getId())
                .details(orders)
                .build();
    }

    private Customer getCustomer(OrderSummary orderSummary) {
        return Customer
                .builder()
                .id(orderSummary.getCustomer().getId())
                .name(orderSummary.getCustomer().getName())
                .address(orderSummary.getCustomer().getAddress())
                .build();
    }
}