package com.example.processingservice.consumer;

import com.example.processingservice.model.*;
import com.example.processingservice.service.*;
import com.example.processingservice.publisher.KafkaPublisherService;
import com.example.processingservice.util.PayloadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/*
 * Kafka consumer component responsible for listening to raw IoT data,
 * transforming it into structured enriched data, filtering, validating,
 * enriching it, and publishing it to another Kafka topic for further persistence.
 */

@Component
public class SensorDataListener {

    private final ObjectMapper objectMapper;
    private final DataFilteringService dataFilteringService;
    private final ValidationService validationService;
    private final EnrichmentService enrichmentService;
    private final KafkaPublisherService kafkaPublisherService;

    /**
     * Constructs the listener with required dependencies for processing IoT
     * messages.
     *
     * @param objectMapper          Jackson object mapper for deserialization
     * @param DataFilteringService  service used to filter incoming data
     * @param validationService     service used to validate incoming data
     * @param enrichmentService     service used to enrich data
     * @param kafkaPublisherService service used to publish enriched data
     */

    public SensorDataListener(ObjectMapper objectMapper,
            DataFilteringService dataFilteringService,
            ValidationService validationService,
            EnrichmentService enrichmentService,
            KafkaPublisherService kafkaPublisherService) {
        this.objectMapper = objectMapper;
        this.dataFilteringService = dataFilteringService;
        this.validationService = validationService;
        this.enrichmentService = enrichmentService;
        this.kafkaPublisherService = kafkaPublisherService;
    }

    /**
     * Kafka listener that receives raw IoT messages from the kafka input
     * topic.
     * 
     * @param message the raw JSON message received from Kafka
     */
    @KafkaListener(topics = "${kafka.topic.input}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        try {
            // Deserialize JSON into IoTMessage
            IoTMessage raw = objectMapper.readValue(message, IoTMessage.class);

            System.out.println("processingservice: data received: " + message);

            // Transform raw message into structured EnrichedSensorData
            EnrichedSensorData data = transform(raw);

            // Filter invalid or nonsensical data early
            if (!dataFilteringService.isValid(data)) {
                System.out.println("[Filter] Invalid data skipped from device: " + data.getDeviceId());
                return;
            }

            // Validate the transformed data
            if (!validationService.validate(data)) {
                System.out.println("[validation] Invalid data - skipping from" + data.getDeviceId());
                return;
            }
            // Enrich the data with additional information
            enrichmentService.enrich(data);

            // Generate a UUID for traceability
            data.setId(UUID.randomUUID().toString());

            // Publish to Kafka for persistence service
            kafkaPublisherService.publishEnrichedData(data);

            System.out.println("enriched data: " + data.getDeviceId() + " - " + data.getTimestamp());
            System.out.println("Published enriched data with ID: " + data.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Transforms a raw IoTMessage into an EnrichedSensorData
     * object.
     * This method maps environmental, location, and battery information if
     * available.
     *
     * @param raw the raw IoT message
     *            return an enriched and structured representation of the data
     */

    private EnrichedSensorData transform(IoTMessage raw) {
        EnrichedSensorData data = new EnrichedSensorData();
        data.setDeviceId(raw.getDeviceId());
        data.setTimestamp(raw.getTimestamp());

        Map<String, Object> p = raw.getPayload();
        if (p != null) {
            Environmental env = new Environmental();
            env.setTemperature(PayloadUtil.getDouble(p, "temperature"));
            env.setHumidity(PayloadUtil.getDouble(p, "humidity"));
            env.setPressure(PayloadUtil.getDouble(p, "pressure"));
            data.setEnvironmental(env);

            Location loc = new Location();
            loc.setLatitude(PayloadUtil.getDouble(p, "latitude"));
            loc.setLongitude(PayloadUtil.getDouble(p, "longitude"));
            data.setLocation(loc);

            Battery bat = new Battery();
            bat.setLevel(PayloadUtil.getInt(p, "battery"));
            data.setBattery(bat);
        }

        return data;
    }
}
