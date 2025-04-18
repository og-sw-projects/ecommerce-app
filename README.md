# E-Commerce Microservices Application

This is a simple e-commerce application built using a microservices architecture with Spring Boot and Spring Cloud. The project is fully containerized using Docker and orchestrated with Docker Compose, making it easy to run and manage all services in a consistent environment. It includes unit testing and follows a modular design for maintainability and scalability.

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
- Dockerized with Docker Compose

## Installation Guide

### Prerequisites

- Java 24+
- Maven
- Docker & Docker Compose

### Running the Application

1. **Clone the repository**
   ```bash
   git clone https://github.com/og-sw-projects/ecommerce-app.git
   cd ecommerce-app
   ```
2. **Build the services**
   ```bash
   ./mvnw clean package -DskipTests
   ```
3. **Start all services using Docker Compose**
   ```bash
   docker-compose up --build
   ```
4. **Access the services**
- **Eureka Dashboard**: http://localhost:8761
   
