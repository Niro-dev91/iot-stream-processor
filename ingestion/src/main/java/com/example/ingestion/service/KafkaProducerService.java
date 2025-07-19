package com.example.ingestion.service;

import com.example.ingestion.model.IoTMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service responsible for publishing IoT messages to a Kafka topic.
 * 
 * Uses KafkaTemplate to send messages where the key is the device ID
 * and the value is the IoTMessage payload.
 */
@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, IoTMessage> kafkaTemplate;
    private final String topicName;

    /**
     * Constructs a new instance of KafkaProducerService with the provided
     * Kafka template
     * and the topic name injected from the application configuration.
     *
     * @param kafkaTemplate the Kafka template used to send IoTMessage
     *                      objects
     * @param topicName     the name of the Kafka topic to which messages will be
     *                      sent,
     *                      injected from the application properties
     */
    public KafkaProducerService(KafkaTemplate<String, IoTMessage> kafkaTemplate,
            @Value("${kafka.topic.output}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    /**
     * Sends an IoT message to the configured Kafka topic.
     */
    public void sendMessage(IoTMessage message) {
        kafkaTemplate.send(topicName, message.getDeviceId(), message);
        System.out.println("[KafkaProducer] Sent message from device " + message.getDeviceId());
    }
}
