package com.example.iotdatagenerator.model;

import java.util.Map;

public class IoTMessage {
    private String deviceId;
    private String timestamp;
    private Map<String, Object> payload;

    public IoTMessage() {
    }

    public IoTMessage(String deviceId, String timestamp, Map<String, Object> payload) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.payload = payload;
    }

    // getters and setters

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getPayload() {
        return this.payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

}
