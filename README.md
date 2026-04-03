# corruner-plan-api

REST API for the **Plans** module of the **CoRunner** application.

## Stack

- Java 21
- Spring Boot 3.2
- Gradle 8.7
- PostgreSQL 16 (via Docker Compose)
- Flyway (database migrations)
- Docker

## Architecture

Follows **Clean Architecture** with strict layer boundaries:

```
controller → application → domain
infrastructure → application + domain
```

| Package | Responsibility |
|---|---|
| `domain` | Pure Java entities and enums, no framework dependencies |
| `application` | Use case interfaces, service implementations, DTOs, repository ports |
| `infrastructure` | JPA entities, Spring Data repositories, repository adapters |
| `controller` | REST controllers, exception handlers |

## Getting Started

### Prerequisites

- Java 21
- Docker

### Running locally

```bash
# Start the database
docker compose up -d

# Run the application
./gradlew bootRun --args='--spring.profiles.active=local'
```

Swagger UI is available at `http://localhost:8080/swagger-ui.html`.

## API Endpoints

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/v1/goals` | Create a goal |
| `GET` | `/api/v1/goals/{id}` | Get a goal by UUID |

## Testing

The project has three test suites:

| Suite | Scope | Command |
|---|---|---|
| **Unit tests** | All scenarios for every class in isolation, no Spring context | `./gradlew test` |
| **Component tests** | Main scenarios end-to-end via MockMvc + H2 embedded database (Cucumber BDD) | `./gradlew test` |
| **Integration tests** | Happy paths against real PostgreSQL | `./gradlew integrationTest` |

> Integration tests require the database to be running (`docker compose up -d`).

## Build

```bash
# Build the project
./gradlew build

# Build Docker image
docker build -t corruner-plan-api .
```
