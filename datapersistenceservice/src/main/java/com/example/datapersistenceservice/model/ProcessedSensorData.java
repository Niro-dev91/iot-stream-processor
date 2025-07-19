package com.example.datapersistenceservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/*
 * Represents a processed IoT sensor data record stored in the Elasticsearch
 * index "iot-data".
 * This class models the enriched and processed sensor information including
 * device details,
 * timestamps, environmental metrics, battery status, and location data.
 */

@Document(indexName = "iot-data")
public class ProcessedSensorData {

    @Id
    private String id;
    private String deviceId;
    private String timestamp;
    private String processedAt;

    private String overallStatus;

    private Environmental environmental;
    private Battery battery;
    private Location location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(String processedAt) {
        this.processedAt = processedAt;
    }

    public String getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    public Environmental getEnvironmental() {
        return environmental;
    }

    public void setEnvironmental(Environmental environmental) {
        this.environmental = environmental;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
