package com.order.summary.transformer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransformerApplicationTests {

    @Mock
    private IOrderService orderService;

    @Mock
    private IWriter writer;

    @InjectMocks
    private TransformerApplication app;

    @Test
    public void ShouldVerifyExecutionOfOrderServiceAndWriterService() {
        //Arrange
        var customerOrderView = CustomerOrderViewMockProvider.getCustomerOrderView();
        given(orderService.GetCustomerOrderView()).willReturn(customerOrderView);

        //Act
        app.run();

        //Assert
        verify(orderService, times(1)).GetCustomerOrderView();
        verify(writer, times(1)).writeCustomerOrderView(customerOrderView);
    }

    @Test
    public void ShouldThrowException_WhenOrderServiceReturnsError() {
        //Arrange
        given(orderService.GetCustomerOrderView()).willThrow(new RuntimeException());

        //Act
        //Assert
        assertThatThrownBy(() -> app.run()).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void ShouldThrowException_WhenWriterServiceReturnsError() {
        //Arrange
        var customerOrderView = CustomerOrderViewMockProvider.getCustomerOrderView();
        given(orderService.GetCustomerOrderView()).willReturn(customerOrderView);
        doThrow(new RuntimeException()).when(writer).writeCustomerOrderView(customerOrderView);

        //Act
        //Assert
        assertThatThrownBy(() -> app.run()).isInstanceOf(RuntimeException.class);
    }
}
