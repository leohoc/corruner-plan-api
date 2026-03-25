# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

`corruner-plan-api` is the REST API for the **Plans** module of the **CoRunner** application. Built with Java, Spring Boot, Gradle, and Docker.

## Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.2
- **Build tool:** Gradle 8.7
- **Database:** PostgreSQL 16 (via Docker Compose)
- **Migrations:** Flyway
- **Containerization:** Docker

## Architecture

The codebase follows **Clean Architecture** and **Clean Code** standards. Layer boundaries are strict — dependencies always point inward:

```
controller → application → domain
infrastructure → application + domain
```

Package structure under `com.corruner.plan.api`:
- `domain` — pure Java entities and enums, no framework dependencies
- `application` — use case interfaces, service implementations, DTOs, repository ports
- `infrastructure` — JPA entities, Spring Data repositories, repository adapters, Flyway migrations
- `controller` — REST controllers, exception handlers

Key convention: JPA `@Entity` annotations live only in `infrastructure`, never in `domain`. The `GoalRepository` port in `application` is implemented by `GoalRepositoryAdapter` in `infrastructure`.

## Commands

```bash
# Start the database
docker compose up -d

# Run locally (requires the local profile for datasource config)
./gradlew bootRun --args='--spring.profiles.active=local'

# Build
./gradlew build

# Run tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.corruner.plan.api.SomeTest"

# Build Docker image
docker build -t corruner-plan-api .
```

Swagger UI is available at `http://localhost:8080/swagger-ui.html` when the app is running.