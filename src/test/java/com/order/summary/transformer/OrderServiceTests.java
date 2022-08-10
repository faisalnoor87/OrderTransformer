package com.order.summary.transformer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @Mock
    private IOrderSummaryProvider orderSummaryProvider;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void ShouldReturnOrderSummaries_WhenValidFileExist() throws IOException {
        //Arrange
        var orderSummaries = getOrderSummaries();
        given(orderSummaryProvider.getOrderSummary()).willReturn(List.of(orderSummaries));

        //Act
        var result = orderService.GetCustomerOrderView();

        //Assert
        verify(orderSummaryProvider, times(1)).getOrderSummary();
        assertThat(result.getCustomers().size()).isEqualTo(2);
        var customer1 = result.getCustomers().get(0);
        assertThat(customer1.getId()).isEqualTo("1");
        assertThat(customer1.getName()).isEqualTo("name1");
        assertThat(customer1.getAddress()).isEqualTo("address1");

        var customer2 = result.getCustomers().get(1);
        assertThat(customer2.getId()).isEqualTo("2");
        assertThat(customer2.getName()).isEqualTo("name2");
        assertThat(customer2.getAddress()).isEqualTo("address2");

        assertThat(result.getOrders().size()).isEqualTo(2);
        var order1 = result.getOrders().get(0);
        assertThat(order1.getId()).isEqualTo(100);
        assertThat(order1.getVendor()).isEqualTo("vendor1");
        assertThat(order1.getDate()).isEqualTo("10/10/10");
        assertThat(order1.getDetails().size()).isEqualTo(1);
        assertThat(order1.getDetails().get(0).getItem()).isEqualTo("hat");
        assertThat(order1.getDetails().get(0).getPrice()).isEqualTo(10);
        assertThat(order1.getDetails().get(0).getQuantity()).isEqualTo(100);
        assertThat(order1.getDetails().get(0).getRevenue()).isEqualTo(1000);

        var order2 = result.getOrders().get(1);
        assertThat(order2.getId()).isEqualTo(200);
        assertThat(order2.getVendor()).isEqualTo("vendor2");
        assertThat(order2.getDate()).isEqualTo("20/10/10");
        assertThat(order2.getDetails().size()).isEqualTo(1);
        assertThat(order2.getDetails().get(0).getItem()).isEqualTo("cake");
        assertThat(order2.getDetails().get(0).getPrice()).isEqualTo(50);
        assertThat(order2.getDetails().get(0).getQuantity()).isEqualTo(1);
        assertThat(order2.getDetails().get(0).getRevenue()).isEqualTo(50);
    }

    @Test
    public void ShouldReturnEmptyCustomerOrderView_WhenOrderSummaryProviderReturnsEmptyList() throws IOException {
        //Arrange
        var orderSummaries = new OrderSummary[0];
        given(orderSummaryProvider.getOrderSummary()).willReturn(List.of(orderSummaries));

        //Act
        var result = orderService.GetCustomerOrderView();

        //Assert
        verify(orderSummaryProvider, times(1)).getOrderSummary();
        assertThat(result.getCustomers().size()).isEqualTo(0);
        assertThat(result.getOrders().size()).isEqualTo(0);
    }

    @Test
    public void ShouldThrowException_WhenOrderSummaryProviderReturnsError() throws IOException {
        //Arrange
        doThrow(new IOException()).when(orderSummaryProvider).getOrderSummary();

        //Act
        //Assert
        assertThatThrownBy(() -> orderService.GetCustomerOrderView())
                .isInstanceOf(RuntimeException.class);
    }

    private OrderSummary[] getOrderSummaries() {
        return new OrderSummary[]{
                getCustomer1OrderSummary(),
                getCustomer2OrderSummary()
        };
    }

    private OrderSummary getCustomer1OrderSummary() {
        var customer1 = OrderSummary.Customer.builder()
                .id("1")
                .name("name1")
                .address("address1")
                .build();

        var customer1Order = OrderSummary.Order.builder()
                .quantity(100)
                .price(10)
                .build();

        Map<String, OrderSummary.Order> orderMap = Map.of(
                "hat", customer1Order
        );

        return OrderSummary.builder()
                .id(100)
                .vendor("vendor1")
                .date("10/10/10")
                .customer(customer1)
                .order(orderMap)
                .build();
    }

    private OrderSummary getCustomer2OrderSummary() {
        var customer2 = OrderSummary.Customer.builder()
                .id("2")
                .name("name2")
                .address("address2")
                .build();

        var customer2Order = OrderSummary.Order.builder()
                .quantity(1)
                .price(50)
                .build();

        Map<String, OrderSummary.Order> orderMap = Map.of(
                "cake", customer2Order
        );

        return OrderSummary.builder()
                .id(200)
                .vendor("vendor2")
                .date("20/10/10")
                .customer(customer2)
                .order(orderMap)
                .build();
    }
}
