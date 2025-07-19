package com.example.datapersistenceservice.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

// Configuration class for setting up Elasticsearch operations in the application.

@Configuration
public class ElasticsearchConfig {

    private final ElasticsearchClient elasticsearchClient;

    public ElasticsearchConfig(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    /*
     * Creates a primary ElasticsearchOperations bean using the injected
     * Elasticsearch client.
     * return an instance of ElasticsearchTemplate for performing Elasticsearch
     * operations
     */
    @Bean
    public ElasticsearchOperations elasticsearchOperations() {
        return new ElasticsearchTemplate(elasticsearchClient);
    }

    /*
     * Provides an additional named bean for ElasticsearchOperations.
     * Useful for cases where multiple beans are required or explicit naming is
     * necessary.
     * return the same instance as elasticsearchOperations
     */
    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchOperations elasticsearchTemplate() {
        return elasticsearchOperations();
    }
}