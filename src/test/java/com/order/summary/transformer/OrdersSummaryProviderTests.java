package com.order.summary.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdersSummaryProviderTests {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private IFile file;

    @InjectMocks
    private OrdersSummaryProvider ordersSummaryProvider;

    @Test
    public void ShouldReturnOrderSummaries_WhenValidFileExist() throws IOException {
        //Arrange
        var orderSummaries = getOrderSummaries();
        var bytes = new ObjectMapper().writeValueAsBytes(orderSummaries);

        given(file.readFile(anyString())).willReturn(bytes);
        given(objectMapper.readValue(bytes, OrderSummary[].class)).willReturn(getOrderSummaries());

        //Act
        var result = ordersSummaryProvider.getOrderSummary();

        //Assert
        verify(file, times(1)).readFile(anyString());
        assertThat(result.get(0).getId()).isEqualTo(100);
        assertThat(result.get(0).getDate()).isEqualTo("10/10/10");
        assertThat(result.get(0).getVendor()).isEqualTo("vendor");

        assertThat(result.get(0).getCustomer().getId()).isEqualTo("1");
        assertThat(result.get(0).getCustomer().getName()).isEqualTo("name");
        assertThat(result.get(0).getCustomer().getAddress()).isEqualTo("address");

        assertThat(result.get(0).getOrder().get("hat").getPrice()).isEqualTo(10);
        assertThat(result.get(0).getOrder().get("hat").getQuantity()).isEqualTo(100);
    }

    @Test
    public void ShouldReturnEmptyOrderSummaries_WhenFileHasNoData() throws IOException {
        //Arrange
        var orderSummaries = new OrderSummary[0];
        var bytes = new ObjectMapper().writeValueAsBytes(orderSummaries);

        given(file.readFile(anyString())).willReturn(bytes);
        given(objectMapper.readValue(bytes, OrderSummary[].class)).willReturn(orderSummaries);

        //Act
        var result = ordersSummaryProvider.getOrderSummary();

        //Assert
        verify(file, times(1)).readFile(anyString());
        assertThat((long) result.size()).isEqualTo(0);
    }

    @Test
    public void ShouldThrowException_WhenFileServiceReturnsError_WhenReadingFile() throws IOException{
        //Arrange
        doThrow(new IOException()).when(file).readFile(anyString());

        //Act
        //Assert
        assertThatThrownBy(() -> ordersSummaryProvider.getOrderSummary())
                .isInstanceOf(RuntimeException.class);
    }

    private OrderSummary[] getOrderSummaries() {
        var customer = OrderSummary.Customer.builder()
                .id("1")
                .name("name")
                .address("address")
                .build();

        var order = OrderSummary.Order.builder()
                .quantity(100)
                .price(10)
                .build();

        Map<String, OrderSummary.Order> orderMap = Map.of(
                "hat", order
        );

        var orderSummary = OrderSummary.builder()
                .id(100)
                .vendor("vendor")
                .date("10/10/10")
                .customer(customer)
                .order(orderMap)
                .build();

        return new OrderSummary[]{ orderSummary };
    }
}
