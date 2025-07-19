package com.example.ingestion.validation;

import com.example.ingestion.model.IoTMessage;
import org.springframework.stereotype.Component;

@Component
public class IoTMessageValidator {

    /**
     * Validates the IoTMessage for required fields
     *
     * @param msg the IoTMessage to validate
     *            return true if valid, false otherwise
     */
    public boolean isValid(IoTMessage msg) {
        if (msg == null) {
            return false;
        }

        // deviceId must not be null or empty
        if (msg.getDeviceId() == null || msg.getDeviceId().trim().isEmpty()) {
            return false;
        }

        // timestamp must not be null
        if (msg.getTimestamp() == null) {
            return false;
        }

        // payload must not be null or empty
        if (msg.getPayload() == null || msg.getPayload().isEmpty()) {
            return false;
        }

        return true;
    }
}
