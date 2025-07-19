package com.example.processingservice.service;

import com.example.processingservice.model.EnrichedSensorData;
import org.springframework.stereotype.Service;

import java.time.Instant;

/*
 * Service responsible for enriching sensor data with additional information,
 * such as processing timestamp and status flags based on sensor readings.
 */

@Service
public class EnrichmentService {

    public void enrich(EnrichedSensorData data) {
        data.setProcessedAt(Instant.now().toString());

        String tempStatus = "TEMP_NOT_AVAILABLE";
        String batStatus = "BATTERY_NOT_AVAILABLE";

        // Check temperature threshold
        if (data.getEnvironmental() != null && data.getEnvironmental().getTemperature() != null) {
            double temp = data.getEnvironmental().getTemperature();
            if (temp > 50) {
                tempStatus = "TEMP_HIGH";
            } else {
                tempStatus = "TEMP_NORMAL";
            }
            data.getEnvironmental().setStatus(tempStatus);
        }

        // Check battery threshold
        if (data.getBattery() != null && data.getBattery().getLevel() != null) {
            int level = data.getBattery().getLevel();
            if (level < 20) {
                batStatus = "LOW_BATTERY";
            } else {
                batStatus = "BATTERY_GOOD";
            }
            data.getBattery().setStatus(batStatus);
        }

        // Build overall status message
        StringBuilder overallStatusBuilder = new StringBuilder();

        if ("TEMP_HIGH".equals(tempStatus)) {
            overallStatusBuilder.append("ALERT: Temperature High");
        }

        if ("LOW_BATTERY".equals(batStatus)) {
            if (overallStatusBuilder.length() > 0) {
                overallStatusBuilder.append("; ");
            }
            overallStatusBuilder.append("ALERT: Low Battery");
        }

        if ("TEMP_NOT_AVAILABLE".equals(tempStatus) && "BATTERY_NOT_AVAILABLE".equals(batStatus)) {
            data.setOverallStatus("UNKNOWN");
        } else if (overallStatusBuilder.length() > 0) {
            data.setOverallStatus(overallStatusBuilder.toString());
        } else {
            data.setOverallStatus("NORMAL");
        }
    }
}
