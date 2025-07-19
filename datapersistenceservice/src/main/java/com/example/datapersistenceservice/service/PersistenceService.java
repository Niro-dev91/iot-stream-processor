package com.example.datapersistenceservice.service;

import com.example.datapersistenceservice.model.ProcessedSensorData;
import com.example.datapersistenceservice.repository.ProcessedDataRepository;
import org.springframework.stereotype.Service;

/*
 * Service responsible for persisting ProcessedSensorData entities
 * into Elasticsearch using the ProcessedDataRepository.
 */

@Service
public class PersistenceService {

    private final ProcessedDataRepository repository;

    // Constructs the service with a repository for data access.

    public PersistenceService(ProcessedDataRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves the given ProcessedSensorData entity to Elasticsearch.
     *
     * @param data the processed sensor data to save
     */
    public void save(ProcessedSensorData data) {
        try {
            repository.save(data);
            System.out.println("Saved successfully document with ID: " + data.getId());
        } catch (Exception e) {
            System.out.println("Failed to save ProcessedSensorData: " + data);
            e.printStackTrace();
        }
    }
}
