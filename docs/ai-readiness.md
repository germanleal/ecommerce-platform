# EC-015 AI Readiness

The AI Gateway is disabled by default and starts without provider credentials.

Configuration is supplied through the environment:

```text
AI_ENABLED=false
AI_PROVIDER=reference
AI_MODEL=reference-model
AI_TIMEOUT=5s
AI_MAX_TOKENS=1024
AI_RATE_LIMIT=30
```

`POST /api/ai/generate` requires a valid JWT, a tenant claim, and an AI or tenant administration authority. The provider and model are selected by server configuration; callers cannot select arbitrary providers or models. Requests are limited per tenant/user, bounded by a timeout, and return normalized errors without provider internals.

`ReferenceAIProvider` is a deterministic local adapter used for readiness tests. A future provider implements `AIProvider` and is registered as a Spring bean without coupling bounded contexts to an SDK.

AI audit metadata is stored in the existing `ai_audit` table. Prompt and response bodies are not persisted or logged. Correlation IDs are propagated through `X-Correlation-Id`.

Run validation with:

```text
docker compose config --quiet
docker compose build ai-gateway-service
docker compose up -d ai-gateway-service
```
