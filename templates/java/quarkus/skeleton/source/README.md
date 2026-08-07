# ${{values.name}}: API

A RESTful ${{values.name}} API built with **Java 21** and **Quarkus 3.33**, providing CRUD operations for persistence.

## 🚀 Quick Start

[Open in Dev Spaces](https://devspaces.apps.prime.pitt.ca/#https://github.com/pittar-rhdh/${{ values.repo }})

```bash
# Start in dev mode with live reload
./mvnw quarkus:dev

# Build the application
mvn package

# Run tests
mvn test
```

## 🏗️ Architecture

- **Framework**: Quarkus 3.33.2.SP1-redhat-00001
- **Database**: PostgreSQL (H2 for unit tests)
- **Persistence**: Hibernate/Panache
- **API Documentation**: OpenAPI 3.0.3
- **Build Tool**: Maven
- **Testing**: JUnit 5, REST Assured

## 📋 Features

- ✅ Contract-first API development
- ✅ CRUD operations for entities
- ✅ PostgreSQL data persistence
- ✅ OpenAPI 3.0 documentation
- ✅ Automated testing suite
- ✅ Observability (Prometheus metrics, OpenTelemetry)

## 📖 API Documentation

Once running, access:
- **Developer UI**: http://localhost:8080/q/dev-ui/
- **Swagger UI**: http://localhost:8080/q/swagger-ui/
- **OpenAPI JSON**: http://localhost:8080/q/openapi.json
- **OpenAPI YAML**: http://localhost:8080/q/openapi.yaml

## 🗃️ Database

- Uses Hibernate's automatic schema generation
- Initial data loaded from `src/main/resources/import.sql`
- Test database: H2 in-memory

## 🔧 Development

### Contract-First Workflow
1. Modify `src/main/openapi/openapi.yaml`
2. Run `mvn package` to generate interfaces
3. Implement generated interfaces in `src/main/java/${{values.javaPackageName}}/${{values.componentId}}/api/impl/`
4. Create/update Panache entities in `src/main/java/${{values.javaPackageName}}/${{values.componentId}}/domain/`

### Project Structure
```
src/
├── main/
│   ├── java/${{values.javaPackageName}}/${{values.componentId}}:/
│   │   ├── api/              # Generated API models
│   │   ├── api/impl/         # API implementations
│   │   ├── domain/           # Panache entities
│   │   └── resource/         # REST resources
│   ├── openapi/
│   │   └── openapi.yaml      # API contract
│   └── resources/
│       ├── application.properties
│       └── import.sql        # Database initialization
└── test/                     # Test classes
```

## 📊 Observability

- **Metrics**: http://localhost:8080/q/metrics
- **Prometheus**: Integrated Micrometer registry
- **Tracing**: OpenTelemetry integration

## 🤝 Contributing

Follow the contract-first development approach:
1. Update OpenAPI specification
2. Generate interfaces with Maven
3. Implement business logic
4. Add comprehensive tests
