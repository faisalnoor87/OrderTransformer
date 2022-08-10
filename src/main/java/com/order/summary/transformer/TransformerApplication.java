package com.order.summary.transformer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class TransformerApplication implements CommandLineRunner {
	private final IOrderService orderService;
	private final IWriter writer;

	public static void main(String[] args) {
		SpringApplication.run(TransformerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("START : run");

		var data = orderService.GetCustomerOrderView();

		writer.writeCustomerOrderView(data);

		log.info("END : run");
	}
}

