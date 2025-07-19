# IoT Data Processing System

This project is a microservices-based IoT data pipeline designed to simulate, ingest, process, and persist sensor data using Apache Kafka, Spring Boot, and Docker.

## Architecture Overview

- **IoTDataGenerator**: Generates simulated IoT sensor data.
- **Ingestion**: Consumes raw data, validates it, and forwards it to the processing service.
- **ProcessingService**: Enhances, filters, categorizes, and sets corresponding status flags along with an overall status message.
- **DataPersistenceService**: Stores valid processed data into Elasticsearch.
- **Kibana**: Used to explore the stored data in Elasticsearch.

## Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 17+
- [MVND](https://github.com/apache/maven-mvnd) (Maven Daemon)

  ### Project Structure
```text
.
├── docker-compose.yml
├── iotdatagenerator/
├── ingestion/
├── processingservice/
└── datapersistenceservice/
```
### Build & Run with Docker Compose

Open a terminal and navigate to the root directory of the project (where the `docker-compose.yml` file is located), then run:

```bash
docker-compose up --build
```
### View Data in Kibana
Navigate to [Kibana Dashboard](http://localhost:5601)

Go to Stack Management > Index Patterns <br/>
Create an index pattern (e.g., iot-data-*) <br/>
Go to Discover to explore the data



