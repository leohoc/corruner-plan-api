# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

`corruner-plan-api` is the REST API for the **Plans** module of the **CoRunner** application. Built with Java, Spring Boot, Gradle, and Docker.

## Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Build tool:** Gradle
- **Containerization:** Docker

## Architecture

The codebase follows **Clean Architecture** and **Clean Code** standards:

- Business logic and domain rules are isolated from frameworks and infrastructure concerns.
- Dependencies point inward: outer layers (web, persistence, external services) depend on inner layers (use cases, domain), never the reverse.
- Each layer communicates through well-defined interfaces/ports.

## Commands

```bash
# Build
./gradlew build

# Run locally
./gradlew bootRun

# Run tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.corruner.plan.api.SomeTest"

# Build Docker image
docker build -t corruner-plan-api .

# Run with Docker
docker run -p 8080:8080 corruner-plan-api
```

Swagger UI is available at `http://localhost:8080/swagger-ui.html` when the app is running.