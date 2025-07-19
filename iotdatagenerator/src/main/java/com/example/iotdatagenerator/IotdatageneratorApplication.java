package com.example.iotdatagenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// enable spring scheduling
@EnableScheduling
public class IotdatageneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotdatageneratorApplication.class, args);
	}

}
