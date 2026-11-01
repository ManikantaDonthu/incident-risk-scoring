# AI corrections

The main correction was around Java version selection during validation.

## Correction 1

The initial attempt targeted Java 25, but Spring Boot 3.5.x and the project configuration are aligned to the current LTS baseline of Java 21. Java 25 does not represent the supported LTS target for this project context and produced a build mismatch during validation.

### What changed

- `pom.xml` was corrected to use `java.version` = `21`
- Docker build/runtime images were restored to `eclipse-temurin-21`
- README documentation was corrected to reflect Java 21 as the supported runtime

## Correction 2

The project initially included a Kafka setup, but it was added as a lightweight integration rather than altering the core incident risk scoring logic. The scoring and policy rules remained the source of truth and were not changed by the messaging layer.

### What changed

- Added Kafka configuration and messaging classes in the `config` and `messaging` packages
- Kept the scoring service and policy logic isolated and unchanged
- Verified the app still compiles with the Java 21 baseline

## Final result

The project now reflects a valid Java 21 / Spring Boot 3.5.x configuration while keeping the required risk scoring logic and deployment setup intact.