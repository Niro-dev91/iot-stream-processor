package com.example.processingservice.publisher;

import com.example.processingservice.model.EnrichedSensorData;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/*
 * Service responsible for publishing enriched IoT sensor data to a Kafka topic.
 * Serializes EnrichedSensorData to JSON and publishes it to the topic
 * iot-processed-data using KafkaTemplate.
 */
@Service
public class KafkaPublisherService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topic;

    // Constructs the KafkaPublisherService with necessary dependencies.

    public KafkaPublisherService(KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper,
            @Value("${kafka.output.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }

    public void publishEnrichedData(EnrichedSensorData data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            kafkaTemplate.send(topic, json);
        } catch (Exception e) {
            System.err.println("Failed to publish enriched data to Kafka");
            e.printStackTrace();
        }
    }
}
