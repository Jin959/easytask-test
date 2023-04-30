package com.easytask.easytask;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class EasytaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasytaskApplication.class, args);

	}

}
