package com.example.iotdatagenerator.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.kafka.core.KafkaAdmin;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

//kafka health check
@Service
public class KafkaHealthChecker {

    private final KafkaAdmin kafkaAdmin;

    public KafkaHealthChecker(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    public boolean isKafkaAvailable() {
        try {
            Map<String, Object> config = kafkaAdmin.getConfigurationProperties();
            AdminClient client = AdminClient.create(config);
            DescribeClusterResult result = client.describeCluster();
            KafkaFuture<String> clusterId = result.clusterId();
            clusterId.get(3, TimeUnit.SECONDS); // wait up to 3s
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
