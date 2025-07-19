package com.example.datapersistenceservice.consumer;

import com.example.datapersistenceservice.model.*;
import com.example.datapersistenceservice.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/* 
 * Kafka listener that consumes processed sensor data messages from the kafka topic
 * and stores them using the PersistenceService.
 */
@Component
public class ProcessedDataListener {

    private final ObjectMapper objectMapper;
    private final PersistenceService persistenceService;

    /**
     * Constructor for dependency injection.
     *
     * @param objectMapper       Jackson object mapper for JSON deserialization.
     * @param persistenceService Service to persist the processed sensor data.
     */

    public ProcessedDataListener(ObjectMapper objectMapper,
            PersistenceService persistenceService) {
        this.objectMapper = objectMapper;
        this.persistenceService = persistenceService;
    }

    /**
     * Kafka consumer method that listens to the kafka topic.
     * Converts the JSON message into a ProcessedSensorData object and saves
     * it.
     *
     * @param message the incoming Kafka message as a JSON string
     */
    @KafkaListener(topics = "${kafka.topic.input}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        try {
            System.out.println("datapersistenceservice: data received: " + message);
            ProcessedSensorData data = objectMapper.readValue(message, ProcessedSensorData.class);
            persistenceService.save(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
