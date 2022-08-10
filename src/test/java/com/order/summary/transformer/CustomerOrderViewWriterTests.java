package com.order.summary.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerOrderViewWriterTests {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private IFile file;

    @InjectMocks
    private CustomerOrderViewWriter writer;

    @Test
    public void ShouldWriteCustomerOrderView_ToTheFile_WhenCustomerOrderViewIsValid() throws IOException {
        //Arrange
        var customerOrderView = CustomerOrderViewMockProvider.getCustomerOrderView();

        //Act
        writer.writeCustomerOrderView(customerOrderView);

        //Assert
        verify(file, times(1)).writeFile(anyString(), any());
    }

    @Test
    public void ShouldWriteEmptyFile_WhenCustomerOrderViewIsNull() throws IOException{
        //Arrange

        //Act
        writer.writeCustomerOrderView(null);

        //Assert
        verify(file, times(1)).writeFile(anyString(), any());
    }

    @Test
    public void ShouldThrowException_WhenUnableToWriteCustomerOrderViewToTheFile() throws IOException{
        //Arrange
        var customerOrderView = CustomerOrderViewMockProvider.getCustomerOrderView();
        doThrow(new IOException()).when(file).writeFile(anyString(), any());

        //Act
        //Assert
        assertThatThrownBy(() -> writer.writeCustomerOrderView(customerOrderView))
                .isInstanceOf(RuntimeException.class);
    }
}
