# Incident Case Risk Scoring
Java 21 + Spring Boot + Spring Data JPA + H2 (local) / PostgreSQL (Docker) + plain HTML UI.

## Features
- Eight seeded alerts across phishing, malware, network_scan and credential_access.
- Create a case; add/remove alerts; apply policies; see score summary.
- Recalculates score from current case state on every score request.

## Scoring
- Raw score = sum of all alert base scores.
- REPEAT_NOISE_DAMPING: only the highest network_scan alert contributes to adjusted score.
- CRITICAL_ASSET_BOOST: floor(pre-boost score * 20%), capped at 30.
- APT_CAMPAIGN_LINK: +25 only when pre-boost score >= 50.
- At most one escalation policy; repeat damping can combine with either.
- Final score capped 100. Severity: 0-29 LOW, 30-59 MEDIUM, 60-84 HIGH, 85-100 CRITICAL.

## Run locally
JDK 21 and Maven 3.9+: `mvn spring-boot:run`. Open http://localhost:8080.
H2 console: http://localhost:8080/h2-console.

## Docker / PostgreSQL
`docker compose up --build`, then open http://localhost:8080.
Demo credentials are for local assessment use only; replace them with secrets for deployment.

## API
- GET `/api/alerts`
- GET `/api/cases`
- POST `/api/cases` body `{"name":"Incident 1"}`
- GET `/api/cases/{id}`
- POST `/api/cases/{caseId}/alerts/{alertId}`
- DELETE `/api/cases/{caseId}/alerts/{alertId}`
- PUT `/api/cases/{id}/policies` body `{"policies":["REPEAT_NOISE_DAMPING","CRITICAL_ASSET_BOOST"]}`
- GET `/api/cases/{id}/score`

## Tests
`mvn test`

## Design assumptions
Scores are computed on demand to avoid stale summaries. The raw score remains the unadjusted sum; policy effects are visible in adjustments and final score. No authentication is implemented because the brief says it is not required. H2 is the local default; Docker Compose uses PostgreSQL.

## Improvements with more time
Add Flyway migrations, DTO response models (avoid exposing entities), integration tests for every boundary and policy combination, structured error responses, pagination, optimistic locking, audit history, and production secrets management.

## Submission evidence
Record the exact AI prompts in `docs/ai-prompts.md`; actual start/end time and token usage in `docs/build-log.md`; and factual corrections in `docs/ai-corrections.md`. Do not estimate these details.
