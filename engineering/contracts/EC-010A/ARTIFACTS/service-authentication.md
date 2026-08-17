# Service authentication

Internal clients are represented in Keycloak as confidential service clients. Client Credentials is the required flow; user tokens must not be shared as service credentials. Client secrets come from environment/secret management and are not committed.

Runtime proof of token acquisition remains pending until Keycloak is running.
