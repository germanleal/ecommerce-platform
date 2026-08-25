# Order Technical Debt

| Priority | Description | Impact | Recommendation |
|---|---|---|---|
| High | Runtime E2E against deployed PostgreSQL/Kafka/Keycloak was not executed in this workspace | Deployment wiring remains environment-dependent | Execute the platform smoke suite before production release |
| High | Cart completion endpoint is an external EC-016 prerequisite | Checkout cannot finalize a live cart until the contract is deployed | Validate/activate the existing Commerce completion contract |
| Medium | API pagination and load/concurrency benchmarks are not yet automated | Large order history queries need operational tuning | Add pagination and performance tests in platform hardening |
| Low | Structured log fields can be expanded with user/order/cart MDC enrichment | Trace search could be richer | Extend the shared observability middleware |
