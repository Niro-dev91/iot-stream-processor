package com.example.iotdatagenerator.service;

import com.example.iotdatagenerator.model.IoTMessage;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

/*
 * Service class responsible for generating random IoT sensor data.
 * This class simulates devices sending telemetry data such as temperature,
 * humidity, pressure, location, and battery level.
 */

@Service
public class DataGenerationService {
    private final Random random = new Random();

    /**
     * Generates a single random IoT message for a given device ID.
     * The message may include:
     * 
     * Temperature (15°C - 35°C)
     * Humidity (30% - 100%)
     * Pressure (950 hPa - 1050 hPa)
     * GPS Coordinates (latitude and longitude)
     * Battery level (0% - 100%)
     * 
     * @param deviceId : the ID of the device sending the message
     *                 return an IoTMessage with a generated payload and timestamp
     */
    public IoTMessage generateRandomMessage(String deviceId) {
        Map<String, Object> payload = new HashMap<>();
        if (random.nextBoolean())
            payload.put("temperature", 15 + random.nextDouble() * 20);
        if (random.nextBoolean())
            payload.put("humidity", 30 + random.nextDouble() * 70);
        if (random.nextBoolean())
            payload.put("pressure", 950 + random.nextDouble() * 100);
        if (random.nextBoolean()) {
            payload.put("latitude", -90 + random.nextDouble() * 180);
            payload.put("longitude", -180 + random.nextDouble() * 360);
        }
        if (random.nextBoolean())
            payload.put("battery", random.nextInt(101));
        return new IoTMessage(deviceId, Instant.now().toString(), payload);
    }

    /**
     * Generates a batch of IoT messages.
     * For each device, it generates a fixed number of random messages.
     *
     * @param deviceCount       : the number of devices to simulate
     * @param messagesPerDevice : the number of messages to generate per device
     *                          return a list of IoTMessage objects representing the
     *                          generated data
     */
    public List<IoTMessage> generateBatch(int deviceCount, int messagesPerDevice) {
        List<IoTMessage> messages = new ArrayList<>();
        for (int i = 1; i <= deviceCount; i++) {
            String deviceId = "device-" + i;
            for (int j = 0; j < messagesPerDevice; j++) {
                messages.add(generateRandomMessage(deviceId));
            }
        }
        return messages;
    }
}
