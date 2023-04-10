package com.easytask.easytask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class EasytaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasytaskApplication.class, args);

	}

}
