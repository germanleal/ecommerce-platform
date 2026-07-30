# Order E2E Report

Unit and application boundary validations pass with `mvn -q test`, including empty-cart rejection, lifecycle invariants and tenant isolation. Full runtime E2E requires the EC-016 deployment with PostgreSQL, Kafka, Keycloak and the external cart-completion endpoint enabled; that environment was not started in this validation run.
