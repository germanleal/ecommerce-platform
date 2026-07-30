# API Error Standard

```json
{ "code": "PRODUCT_NOT_FOUND", "message": "The requested product was not found.", "correlationId": "" }
```

Use 400 for validation, 401 for unauthenticated, 403 for forbidden, 404 for missing resources and 500 for unexpected errors. Never expose stack traces, credentials or internal configuration.
