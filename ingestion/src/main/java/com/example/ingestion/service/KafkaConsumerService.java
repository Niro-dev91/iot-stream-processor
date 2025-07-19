package com.example.ingestion.service;

import com.example.ingestion.model.IoTMessage;
import com.example.ingestion.validation.IoTMessageValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Service class responsible for consuming IoT messages from a Kafka topic,
 * validating them, and forwarding valid messages to a Kafka producer.
 */

@Service
public class KafkaConsumerService {

    private final ObjectMapper objectMapper;

    private final KafkaProducerService kafkaProducerService;
    private final IoTMessageValidator validator;

    /**
     * Constructor that initializes dependencies and configures the ObjectMapper
     * with JavaTimeModule to support Java 8+ date/time types.
     *
     * @param kafkaProducerService the Kafka producer service to forward valid
     *                             messages
     * @param validator            the validator used to validate incoming IoT
     *                             messages
     */

    public KafkaConsumerService(KafkaProducerService kafkaProducerService,
            IoTMessageValidator validator) {
        this.kafkaProducerService = kafkaProducerService;
        this.validator = validator;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());

    }

    /**
     * Consumes a JSON message from the configured Kafka topic, deserializes it to
     * an IoTMessage, validates it, and sends it to the producer service if valid.
     *
     * @param messageJson the raw JSON message consumed from Kafka
     */

    @KafkaListener(topics = "${kafka.topic.input}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAndValidate(String messageJson) {
        try {
            IoTMessage message = objectMapper.readValue(messageJson, IoTMessage.class);

            if (validator.isValid(message)) {
                System.out.println("[Consumer] Valid message from device: " + message.getDeviceId());
                kafkaProducerService.sendMessage(message);
            } else {
                System.out.println("[Consumer] Invalid message skipped from device: " + message.getDeviceId());
            }
        } catch (Exception e) {
            System.err.println("[Consumer] Failed to process message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
