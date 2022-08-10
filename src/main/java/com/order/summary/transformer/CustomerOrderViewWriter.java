package com.order.summary.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerOrderViewWriter implements IWriter {
    private final ObjectMapper objectMapper;
    private final IFile file;

    @Override
    public void writeCustomerOrderView(CustomerOrderView result) {
        log.info("START : writeCustomerOrderView");

        try {
            var fileName = "data-transformed.json"; //fileName should come from a config or an identifier should be passed by the client.
            var data = objectMapper.writeValueAsBytes(result);
            file.writeFile(fileName, data);
        } catch (IOException e) {
            log.error("Unable to write file.");
            throw new RuntimeException(); //TODO Implement Domain Exception to get the better view of the errors.
        }
        log.info("END : writeCustomerOrderView");
    }
}

