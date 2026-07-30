# Template ID: TPL-006

## Nombre
React Marketplace Application

## Versión
1.0.0

## Estado
Approved

## Autor
Architecture Governance Board

## Fecha de creación
2026-07-29

## Compatibilidad
EC-005, EC-006

## Engineering Contract de origen
EC-006

## Objetivo
Definir la plantilla oficial para la aplicación React del marketplace.

## Alcance
- Estructura de aplicación React estándar.
- Carpetas mínimas obligatorias.
- Documentación requerida.

## Estructura requerida
```
marketplace/
├── src/
│   ├── app/
│   ├── pages/
│   ├── features/
│   ├── components/
│   ├── layouts/
│   ├── hooks/
│   ├── services/
│   ├── store/
│   ├── contexts/
│   ├── routes/
│   ├── validators/
│   ├── types/
│   ├── assets/
│   ├── styles/
│   └── providers/
├── public/
├── docs/
├── tests/
├── scripts/
├── README.md
├── CHANGELOG.md
├── package.json
├── LICENSE
├── .gitignore
├── .editorconfig
└── .gitattributes
```

## Reglas clave
- La organización principal es por feature.
- No mezclar componentes globales dentro de `features/`.
- Mantener dependencias documentadas.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
