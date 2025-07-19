# IoT Data Processing System

This project is a microservices-based IoT data pipeline designed to simulate, ingest, process, and persist sensor data, with visualization enabled using Apache Kafka, Spring Boot, Docker, Elasticsearch, and Kibana.

## Architecture Overview

- **IoTDataGenerator**: Generates simulated IoT sensor data.
- **Ingestion**: Consumes raw data, validates it, and forwards it to the processing service.
- **ProcessingService**: Validates, Enhances, filters, categorizes, and sets corresponding status flags along with an overall status message.
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
Navigate to Kibana Dashboard http://localhost:5601

Navigate to Stack Management > index patterns(e.g., iot-data-) to
create or view Elasticsearch indices.<br/> 
or<br/>
Navigate to Discover > Create data view > select index pattern (iotdata-*)



