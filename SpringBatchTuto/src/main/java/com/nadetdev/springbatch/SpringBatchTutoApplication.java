package com.nadetdev.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.nadetdev.springbatch.config", "com.nadetdev.springbatch.service, com.nadetdev.springbatch.listener,"
		+ "com.nadetdev.springbatch.reader, com.nadetdev.springbatch.processor, com.nadetdev.springbatch.writer;"})
public class SpringBatchTutoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchTutoApplication.class, args);
	}

}
