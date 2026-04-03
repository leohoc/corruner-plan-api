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

## Testing Strategy

There are three test suites, each with a distinct scope:

- **Unit tests** — cover all scenarios for every class in isolation (services, mappers, domain logic). Mock all dependencies. No Spring context.
- **Component tests** — Cucumber BDD tests using H2 embedded database. Cover the main scenarios (happy paths and key error cases) end-to-end through the full stack via MockMvc. Live under `src/test/java/.../component/` with feature files at `src/test/resources/features/`.
- **Integration tests** — validate end-to-end happy paths against a real running environment (PostgreSQL). Confirm the system works as a whole.

When adding a new feature, all three suites must be updated accordingly.

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

# Run integration tests (requires docker compose up -d first)
./gradlew integrationTest
```

Swagger UI is available at `http://localhost:8080/swagger-ui.html` when the app is running.