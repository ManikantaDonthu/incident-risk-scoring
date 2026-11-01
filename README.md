# Incident Case Risk Scoring

A Spring Boot application for managing security incident cases, attaching alerts, applying scoring policies, and generating a risk summary. The app exposes a simple HTML UI and a REST API for case management and scoring.

## What it does

- Lists available security alerts from a seeded datastore
- Creates and persists incident cases
- Allows alerts to be added to or removed from a case
- Applies scoring policies to the case
- Computes a raw score, adjustment summary, final capped score, and severity band
- Supports local development and Docker-based deployment

## Tech stack

- Java 21
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA
- H2 database for local development
- PostgreSQL for Docker-based deployment
- Maven
- Plain HTML/JavaScript frontend

## Architecture and design decisions

- Layered architecture: controller, service, repository, model, DTO, config
- JPA entities persist cases and alerts with many-to-many relationships
- Scoring is computed on demand instead of storing stale summaries in the database
- Policy evaluation happens from the current case state, so if a case changes it is recalculated correctly
- H2 is used locally for simple startup without external services; Docker Compose switches to PostgreSQL
- Seed data creates at least eight alerts across phishing, malware, network_scan, and credential_access

## Local run instructions

Prerequisites:
- JDK 21
- Maven 3.9+

1. Clone the repo
2. In the project root, run:
   ```bash
   mvn spring-boot:run
   ```
3. Open http://localhost:8080
4. H2 console is available at http://localhost:8080/h2-console

## Docker / Compose deployment

This project includes a Compose file for running the app with PostgreSQL.

1. From the project root:
   ```bash
   docker compose up --build
   ```
2. Application: http://localhost:8080
3. Database: localhost:5432

### Required environment variables

For Docker Compose, the defaults are defined in `docker-compose.yml`:

- `SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/riskdb`
- `SPRING_DATASOURCE_USERNAME=riskuser`
- `SPRING_DATASOURCE_PASSWORD=riskpass`
- `SPRING_DATASOURCE_DRIVER=org.postgresql.Driver`
- `PORT=8080`

You can override them in a `.env` file or by editing the environment section.

## Database seeding

The app seeds alerts automatically on startup when the alert table is empty using `DataSeeder`.

The seeded categories include:
- phishing
- malware
- network_scan
- credential_access

## API endpoints

### Alerts
- `GET /api/alerts` — list all available alerts

### Cases
- `GET /api/cases` — list all cases
- `POST /api/cases` — create a case
  body:
  ```json
  { "name": "Incident 1" }
  ```
- `GET /api/cases/{id}` — fetch a case
- `POST /api/cases/{caseId}/alerts/{alertId}` — add an alert to a case
- `DELETE /api/cases/{caseId}/alerts/{alertId}` — remove an alert from a case
- `PUT /api/cases/{id}/policies` — apply one or more policies
  body:
  ```json
  { "policies": ["REPEAT_NOISE_DAMPING", "CRITICAL_ASSET_BOOST"] }
  ```
- `GET /api/cases/{id}/score` — return the raw score, adjustments, final score, severity, and policy list

## Business rules implemented

- Raw score is the sum of base scores for all alerts in the case
- Final score is capped at 100
- Severity bands:
  - 0-29 Low
  - 30-59 Medium
  - 60-84 High
  - 85-100 Critical
- `REPEAT_NOISE_DAMPING`: within `network_scan`, only the highest base score counts; others are ignored
- `CRITICAL_ASSET_BOOST`: adds 20% of the current score, capped at 30
- `APT_CAMPAIGN_LINK`: adds 25 only when the score before boosting is at least 50
- Only one escalation policy can be active at a time: `CRITICAL_ASSET_BOOST` or `APT_CAMPAIGN_LINK`
- `REPEAT_NOISE_DAMPING` can be combined with either escalation policy
- Fractional values are floored before final scoring
- Scoring is recalculated from the current case state each time so stale policy data cannot remain active incorrectly

## Tests

Run:

```bash
mvn test
```

## Known limitations

- No authentication or authorization has been added
- This is a demo-style app rather than a full enterprise-grade incident system
- No API versioning or pagination is implemented yet
- No production secrets management or hardened deployment configuration has been added
- No explicit Flyway or schema migration pipeline is present

## Security and domain assumptions

- Local H2 is acceptable for development and evaluation only
- Docker Compose uses local credentials intended for demo purposes only
- No authentication was required by the task, so it was not added
- The solution assumes the score is computed from the current alert set and active policies each time a request is made

## What would improve with more time

- Add Flyway migrations and DB schema versioning
- Replace entity exposure in controllers with DTOs
- Add integration and boundary-value tests for all policy combinations
- Add structured error handling and validation
- Add pagination, auditing, and optimistic locking
- Add secure secret management and environment-specific configuration
- Add Kafka event flow and stronger async integration patterns

## Repository and submission notes

This repository includes documentation for:
- prompts used
- build log
- AI corrections
- token usage tracking

See:
- `docs/ai-prompts.md`
- `docs/build-log.md`
- `docs/ai-corrections.md`
- `docs/token-usage.md`

## Example local usage

1. Start the app:
   ```bash
   mvn spring-boot:run
   ```
2. Open the app in a browser
3. Create a case
4. Add alerts
5. Choose policies
6. View the score summary
