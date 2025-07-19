package com.example.iotdatagenerator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.iotdatagenerator.model.IoTMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for sending IoT messages directly to a Kafka topic.
 */
@Service
public class DataSendingService {

    private final KafkaTemplate<String, IoTMessage> kafkaTemplate;
    private final String topicName;
    private final ObjectMapper objectMapper = new ObjectMapper(); // for JSON conversion

    /**
     * Constructs the service with KafkaTemplate and topic name.
     *
     * @param kafkaTemplate KafkaTemplate to send messages
     * @param topicName     Kafka topic to send messages to
     */
    public DataSendingService(KafkaTemplate<String, IoTMessage> kafkaTemplate,
            @Value("${kafka.topic.name}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    /**
     * Sends a single IoT message to the configured Kafka topic.
     *
     * @param message the IoTMessage to send
     */
    public void sendMessage(IoTMessage message) {
        kafkaTemplate.send(topicName, message.getDeviceId(), message);
        try {
            String json = objectMapper.writeValueAsString(message); // Convert to JSON string
            System.out.println("[KafkaSender] Sent JSON message: " + json);
        } catch (Exception e) {
            System.out.println("[KafkaSender] Failed to convert message to JSON: " + e.getMessage());
        }
        System.out.println("[KafkaSender] Sent message from device: " + message.getDeviceId());
    }

    /**
     * Sends a batch of IoT messages sequentially with a short delay between each
     * send.
     *
     * @param messages the list of IoTMessage objects to send
     *                 throws InterruptedException if the thread sleep is
     *                 interrupted
     */
    public void sendBatch(List<IoTMessage> messages) throws InterruptedException {
        for (IoTMessage msg : messages) {
            sendMessage(msg);
            Thread.sleep(50); // simulate a small delay
        }
    }
}
