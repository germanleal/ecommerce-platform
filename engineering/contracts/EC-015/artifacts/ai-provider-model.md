# AI Provider Model

El AI Gateway dependerá de un Provider Port abstracto. Cada adapter futuro encapsulará autenticación, límites, streaming, errores y formato del proveedor.

Los proveedores posibles —OpenAI, Azure OpenAI, Anthropic, Google, Ollama o modelos internos— son reemplazables y no se seleccionan en esta fase. Secretos vivirán en un secret provider, nunca en prompts, logs o configuración de tenant.
