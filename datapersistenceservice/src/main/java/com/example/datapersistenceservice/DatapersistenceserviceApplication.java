package com.example.datapersistenceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Main class for the Data Persistence Service Spring Boot application.
 * <p>
 * Enables Elasticsearch repositories and Spring Retry functionality.
 * </p>
 */

@SpringBootApplication
@EnableRetry
@EnableElasticsearchRepositories(basePackages = "com.example.datapersistenceservice.repository")
public class DatapersistenceserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatapersistenceserviceApplication.class, args);
	}

}
