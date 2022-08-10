package com.order.summary.transformer;

import java.util.List;

public class CustomerOrderViewMockProvider {
    public static CustomerOrderView getCustomerOrderView(){
        return CustomerOrderView.builder()
                .customers(getCustomers())
                .orders(getOrders())
                .build();
    }

    public static List<Customer> getCustomers(){
        var customer = Customer.builder()
                .id("1")
                .name("test")
                .address("address")
                .build();

        return List.of(customer);
    }

    public static List<Order> getOrders(){
        var orderDetail = Order.OrderDetail.builder().price(10).item("hat").quantity(100).revenue(1000).build();

        var order = Order.builder()
                .id(100)
                .vendor("22")
                .customerId("1")
                .details((List.of(orderDetail)))
                .build();

        return List.of(order);
    }
}
