# Docker Security Guideline

- Usar imágenes oficiales y versiones fijadas.
- Prohibido `latest`.
- Reducir capas y superficie de ataque.
- Preferir usuarios no root.
- No incluir secretos en imágenes.
- Ejecutar escaneo Trivy o Grype antes de publicar.
