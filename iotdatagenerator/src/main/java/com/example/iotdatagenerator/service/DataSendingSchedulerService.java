package com.example.iotdatagenerator.service;

import com.example.iotdatagenerator.model.IoTMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Scheduler service that periodically generates and sends batches of IoT
 * messages.
 * This service uses DataGenerationService to simulate IoT data
 * and DataSendingService to send the data to a target destination.
 */

@Service
public class DataSendingSchedulerService {
    private final DataGenerationService generator;
    private final DataSendingService sender;
    private final KafkaHealthChecker kafkaHealthChecker;

    // Number of devices to simulate. Default is 5.
    private int deviceCount = 5;

    // Number of messages to generate per device. Default is 2.
    private int messagesPerDevice = 2;

    /**
     * Constructor to inject dependencies.
     *
     * @param generator the service used to generate IoT data
     * @param sender    the service used to send IoT data
     */

    public DataSendingSchedulerService(DataGenerationService generator, DataSendingService sender,
            KafkaHealthChecker kafkaHealthChecker) {
        this.generator = generator;
        this.sender = sender;
        this.kafkaHealthChecker = kafkaHealthChecker;
    }

    /**
     * Scheduled task that runs every 30 seconds to:
     * Generate a batch of IoT messages for a configured number of devices
     * Send the generated data using the DataSendingService
     * 
     * throws Exception if sending fails
     */
    @Scheduled(fixedRate = 30000)
    public void runPeriodicGeneration() {
        try {
            // check whether Kafka is available
            if (!kafkaHealthChecker.isKafkaAvailable()) {
                System.out.println("Kafka not available yet. Skipping data generation.");
                return;
            }
            System.out.println("[Scheduler] Generating and sending IoT data...");
            List<IoTMessage> batch = generator.generateBatch(deviceCount, messagesPerDevice);
            sender.sendBatch(batch);
            System.out.println("[Scheduler] Batch sent successfully.");
        } catch (InterruptedException e) {
            System.err.println("[Scheduler] Interrupted! " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("[Scheduler] Exception during scheduled task: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Configure the number of devices and messages per device at
     * runtime."Optional method"
     *
     * @param deviceCount       the number of devices to simulate
     * @param messagesPerDevice the number of messages to generate per device
     */
    public void setConfig(int deviceCount, int messagesPerDevice) {
        this.deviceCount = deviceCount;
        this.messagesPerDevice = messagesPerDevice;
    }
}
