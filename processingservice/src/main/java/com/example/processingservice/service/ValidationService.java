package com.example.processingservice.service;

import com.example.processingservice.model.EnrichedSensorData;
import org.springframework.stereotype.Service;

// Service responsible for validating incoming enriched sensor data.

@Service
public class ValidationService {

    public boolean validate(EnrichedSensorData data) {
        if (data.getDeviceId() == null || data.getDeviceId().isEmpty())
            return false;
        if (data.getTimestamp() == null || data.getTimestamp().isEmpty())
            return false;
        return true;
    }
}
