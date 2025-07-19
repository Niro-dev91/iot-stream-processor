package com.example.processingservice.service;

import com.example.processingservice.model.*;
import org.springframework.stereotype.Service;

/*
 * Service responsible for filtering invalid or corrupt sensor data.
 * This class ensures that only realistically valid sensor data is processed
 * further. It performs sanity checks on:
 * - Temperature (-50 to +60 °C) — accepts if at least one present and valid
 * - Humidity (0–100%) — accepts if at least one present and valid
 * - Pressure (300–1100 hPa) — accepts if at least one present and valid
 * - Battery level (0–100%) (optional)
 * - Latitude (-90 to 90) (mandatory)
 * - Longitude (-180 to 180) (mandatory)
 */

@Service
public class DataFilteringService {

    public boolean isValid(EnrichedSensorData data) {
        Environmental env = data.getEnvironmental();
        Battery battery = data.getBattery();
        Location loc = data.getLocation();

        // Environmental data must exist and have at least one valid value
        if (env == null) {
            return false;
        }

        boolean hasValidEnvironmentalValue = false;

        Double temp = env.getTemperature();
        if (temp != null && temp >= -50 && temp <= 60) {
            hasValidEnvironmentalValue = true;
        }

        Double humidity = env.getHumidity();
        if (humidity != null && humidity >= 0 && humidity <= 100) {
            hasValidEnvironmentalValue = true;
        }

        Double pressure = env.getPressure();
        if (pressure != null && pressure >= 300 && pressure <= 1100) {
            hasValidEnvironmentalValue = true;
        }

        // Reject if no valid environmental data present
        if (!hasValidEnvironmentalValue) {
            return false;
        }

        // Validate location data (mandatory)
        if (loc == null || loc.getLatitude() == null || loc.getLongitude() == null) {
            return false;
        }
        double lat = loc.getLatitude();
        double lon = loc.getLongitude();
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            return false;
        }

        // Battery data is optional, validate only if present
        if (battery != null && battery.getLevel() != null) {
            int level = battery.getLevel();
            if (level < 0 || level > 100) {
                return false;
            }
        }

        return true;
    }
}
