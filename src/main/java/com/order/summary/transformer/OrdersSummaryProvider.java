package com.order.summary.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrdersSummaryProvider implements IOrderSummaryProvider {

    private final ObjectMapper objectMapper;
    private final IFile file;

    @Override
    public List<OrderSummary> getOrderSummary() {
        log.info("START : getOrderSummary");

        var fileName = "data.json"; //fileName should come from a config or an identifier should be passed by the client.
        OrderSummary[] orderSummaries;
        try {
            var data = file.readFile(fileName);
            orderSummaries = objectMapper.readValue(data, OrderSummary[].class);
        }
        catch (IOException e) {
            log.error("Unable to read file.");
            throw new RuntimeException(); //TODO Implement Domain Exception to get the better view of the errors.
        }

        log.info("END : getOrderSummary");

        return List.of(orderSummaries);
    }
}

