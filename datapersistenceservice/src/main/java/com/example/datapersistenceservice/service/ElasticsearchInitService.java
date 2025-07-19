package com.example.datapersistenceservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.example.datapersistenceservice.model.ProcessedSensorData;

import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

/*
 * Service that checks and initializes the Elasticsearch index for
 * ProcessedSensorData.
 * This service runs automatically after bean construction and retries up to 10
 * times
 * with a 3-second delay if any exception occurs, useful during Elasticsearch
 * startup.
 * Active only when the 'test' profile is NOT active.
 */

@Profile("!test")
@Service
public class ElasticsearchInitService {

    private final ElasticsearchOperations elasticsearchOperations;

    public ElasticsearchInitService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /*
     * Checks if the Elasticsearch index for ProcessedSensorData exists.
     * Runs automatically after bean initialization.
     * If the index does not exist, logs the information.
     * Retries up to 10 times with 3 seconds delay on failure.
     */
    @PostConstruct
    @Retryable(value = Exception.class, maxAttempts = 10, backoff = @Backoff(delay = 3000))
    public void init() {
        boolean exists = elasticsearchOperations.indexOps(ProcessedSensorData.class).exists();
        if (exists) {
            System.out.println("Elasticsearch index exists.");
        } else {
            System.out.println("Elasticsearch index does not exist yet.");
        }
    }
}
