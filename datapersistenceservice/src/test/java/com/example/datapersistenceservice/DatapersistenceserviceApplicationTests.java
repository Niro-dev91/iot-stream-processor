package com.example.datapersistenceservice;

import org.junit.jupiter.api.Test;

import com.example.datapersistenceservice.repository.ProcessedDataRepository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

/*
 * Basic Spring Boot test for the Data Persistence Service application.
 * Loads the application context for testing, while excluding Elasticsearch
 * auto-configuration
 * to isolate tests and prevent external dependencies.
 * Uses the 'test' profile and mocks the ProcessedDataRepository to avoid real
 * data access.
 */

@SpringBootTest(properties = {
        "spring.kafka.consumer.group-id=test-group"
})
@EnableAutoConfiguration(exclude = {
        ElasticsearchDataAutoConfiguration.class,
        ElasticsearchRepositoriesAutoConfiguration.class
})
@ActiveProfiles("test")
class DatapersistenceserviceApplicationTests {

    @MockBean
    private ProcessedDataRepository ProcessedDataRepository;

    @Test
    void contextLoads() {
    }
}
