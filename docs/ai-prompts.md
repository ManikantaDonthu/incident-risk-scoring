# AI prompts used

This file records the prompts used during the project build and validation process.

## Prompt 1

"Build a full-stack case risk scoring tool. Backend: Java Spring Boot app with alerts table, case management, policy application, and risk scoring. Frontend: simple HTML view for alerts, case management, and score summary. Include seed data."

## Prompt 2

"Implement the required business rules: raw score sum of alert scores, REPEAT_NOISE_DAMPING, CRITICAL_ASSET_BOOST, APT_CAMPAIGN_LINK, severity bands, and policy validation. Make the scoring summary recalculate from the current case state."

## Prompt 3

"Create the README and deployment docs. Include local run instructions, Docker Compose, environment variables, API endpoints, assumptions, limitations, and future improvements."

## Prompt 4

"Add Kafka support for security alert ingestion: producer, consumer, event class, config, and ingestion service. Keep it lightweight and compatible with the Spring Boot app."

## Prompt 5

"Validate the Java version and build configuration. Ensure the project is aligned to the supported LTS Java version for Spring Boot 3.5.x."