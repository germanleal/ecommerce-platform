# AI Readiness Technical Debt

## Critical

- No real Keycloak, persistence or cross-tenant integration tests. Impact: tenant and authorization guarantees are unproven. Priority immediate. Remediation: Testcontainers and JWT permission matrix.
- No observability implementation beyond audit fields. Impact: future executions would not be traceable. Priority immediate. Remediation: Micrometer/OpenTelemetry and correlation propagation.

## High

- Policy and capability persistence/API validation are incomplete. Impact: orchestration governance is not enforceable. Remediation: persist policies, enforce PolicyEngine in gateway and add API tests.

## Medium

- Context cache is in-memory only. Impact: inconsistent behavior across replicas. Remediation: configurable shared cache when approved.

## Low

- No provider contract tests exist because providers are intentionally absent. Remediation: add adapter contract tests when the first provider is approved.
