package com.example.datapersistenceservice.repository;

import com.example.datapersistenceservice.model.ProcessedSensorData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/*
 * Repository interface for managing ProcessedSensorData entities in
 * Elasticsearch.
 * Extends ElasticsearchRepository to provide CRUD operations on the "iot-data"
 * index.
 */

public interface ProcessedDataRepository extends ElasticsearchRepository<ProcessedSensorData, String> {
}
