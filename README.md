# E-Commerce Microservices Application

This is a simple e-commerce application built using a microservices architecture with Spring Boot and Spring Cloud. It includes unit testing and follows a modular design for maintainability and scalability.

## Microservices Included

- **Product Service** – Manages product details.
- **Order Service** – Handles customer orders.
- **Inventory Service** – Tracks product stock.
- **API Gateway** – Single entry point to all services.
- **Eureka Server** – Enables service discovery.

## Features

- RESTful APIs
- In-memory H2 database
- Service Discovery (Eureka)
- API routing via Spring Cloud Gateway
- Unit tests included

## Installation Guide

### Prerequisites

- Java 24+
- Maven

### Steps

1. **Clone the repo**
   ```bash
   git clone https://github.com/og-sw-projects/ecommerce-app.git
   cd ecommerce-app
   ```
2. **Run Eureka Server**
   ```bash
   cd eurekaserver
   ./mvnw spring-boot:run
   ```
3. **Run API Gateway**
   ```bash
   cd api-gateway
   ./mvnw spring-boot:run
   ```
4. **Run each microservice**
   ```bash
   cd product-service && ./mvnw spring-boot:run
   cd order-service && ./mvnw spring-boot:run
   cd inventory-service && ./mvnw spring-boot:run
   ```