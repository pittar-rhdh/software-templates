# AGENTS.md

## Application Overview

This is a **${{values.componentId}}** built with Java 21 and Quarkus 3.33, providing RESTful interfaces for CRUD operations on fruits. The application follows a contract-first development approach and uses PostgreSQL for data persistence.

## Technology Stack

- **Java**: Version 21
- **Framework**: Quarkus 3.33.2.SP1-redhat-00001
- **Database**: PostgreSQL (H2 for unit tests)
- **Persistence**: Hibernate/Panache
- **API Documentation**: OpenAPI 3.0.3
- **Build Tool**: Maven
- **Testing**: JUnit 5, REST Assured
- **Observability**: OpenTelemetry, Micrometer Prometheus
- **Validation**: Hibernate Validator

## Project Structure

```
src/
├── main/
│   ├── java/${{values.javaPackageName}}/${{values.componentId}}/
│   │   ├── api/              # Generated API models (auto-generated)
│   │   ├── api/impl/         # API implementation classes
│   │   ├── domain/           # Panache entity classes
│   │   └── resource/         # REST resources
│   ├── openapi/
│   │   └── openapi.yaml      # API contract specification
│   └── resources/
│       ├── application.properties
│       └── import.sql        # Database initialization
└── test/                     # Test classes
```

## Development Workflow

### 1. Branch Naming Convention

**Features**: Use pattern `featurename-feature`
```bash
git checkout -b new-categories-feature
```

**Bug Fixes**: Use pattern `bufixname-fix`
```bash
git checkout -b entity-validation-fix
```

### 2. Contract-First API Development

**All API endpoints must be created using the contract-first approach:**

1. **Modify the OpenAPI specification** in `src/main/openapi/openapi.yaml`
2. **Run Maven build** to generate interfaces:
   ```bash
   mvn package
   ```
3. **Implement the generated interface** in `src/main/java/${{values.javaPackageName}}/${{values.componentId}}/api/impl/`
4. **Create/update Panache entities** in `src/main/java/${{values.javaPackageName}}/${{values.componentId}}/domain/`

#### API Package Structure
- **Generated Models**: `${{values.groupId}}.${{values.componentId}}.api` (auto-generated, never modify manually)
- **API Implementations**: `${{values.groupId}}.${{values.componentId}}.api.impl`
- **Domain Entities**: `${{values.groupId}}.${{values.componentId}}.domain`

### 3. Database Persistence Rules

#### ⚠️ CRITICAL: Never return Panache entities directly from API endpoints

**Correct Pattern**:
```java
// Domain entity (Panache)
@Entity
public class FruitEntity extends PanacheEntity {
    public String name;
    public String colour;
}

// API model (auto-generated from OpenAPI)
public class Fruit {
    private Long id;
    private String name;
    private String colour;
}

// In your API implementation
public Fruit getFruitById(Long id) {
    FruitEntity entity = FruitEntity.findById(id);
    if (entity == null) {
        throw new NotFoundException();
    }

    // Map entity to API model
    Fruit fruit = new Fruit();
    fruit.setId(entity.id);
    fruit.setName(entity.name);
    fruit.setColour(entity.colour);
    return fruit;
}
```

## Available Commands

### Development
```bash
# Start in dev mode with live reload
./mvnw quarkus:dev

# Build the application
mvn package

# Run with custom config
./mvnw quarkus:dev -Dquarkus.profile=dev
```

### Testing
```bash
# Run unit tests
mvn test

# Run integration tests
mvn integration-test

# Run all tests
mvn verify
```

### Native Image (Optional)
```bash
# Build native image
mvn package -Dnative

# Run native image
./target/${{values.componentId}}-1.0.0-SNAPSHOT-runner
```

## Configuration

### Database Configuration

**Development/Production** (`application.properties`):
```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=your-username
quarkus.datasource.password=your-password
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/fruitdb
```

**Test** (automatically uses H2):
- H2 in-memory database is configured for tests
- No additional configuration required

### Application Properties

Key configuration properties are in `src/main/resources/application.properties`:

- Database connection settings
- OpenAPI configuration
- Micrometer/Prometheus settings
- OpenTelemetry configuration

## API Documentation

Once the application is running, API documentation is available at:

- **OpenAPI UI**: `http://localhost:8080/q/swagger-ui/`
- **OpenAPI JSON**: `http://localhost:8080/q/openapi.json`
- **OpenAPI YAML**: `http://localhost:8080/q/openapi.yaml`

## Database Schema

The application uses Hibernate's automatic schema generation. Initial data is loaded from `src/main/resources/import.sql`.

## Observability

### Metrics
- Prometheus metrics available at: `http://localhost:8080/q/metrics`
- Micrometer registry integrated for application metrics

### Tracing
- OpenTelemetry integration for distributed tracing
- Configurable via application properties

## Development Best Practices

### 1. Always Use Contract-First
- Modify `openapi.yaml` first
- Run `mvn package` to generate interfaces
- Implement the generated interfaces

### 2. Entity Mapping
- Use separate classes for API models (auto-generated) and domain entities (Panache)
- Implement proper mapping logic in service/repository layers
- Never expose Panache entities in API responses

### 3. Error Handling
- Use appropriate HTTP status codes
- Implement proper exception handling
- Follow OpenAPI response specifications

### 4. Testing
- Write unit tests for business logic
- Use integration tests for API endpoints
- Test both success and failure scenarios

### 5. Database Migrations
- Use `import.sql` for initial data
- Consider using Flyway or Liquibase for production schema migrations

## Common Development Tasks

### Adding a New API Endpoint

1. Edit `src/main/openapi/openapi.yaml`
2. Add your endpoint definition
3. Run `mvn package`
4. Implement the generated interface
5. Create or update corresponding Panache entity
6. Add tests

### Adding Database Fields

1. Update Panache entity in `domain/` package
2. Update OpenAPI schema if field should be exposed via API
3. Run `mvn package` if OpenAPI was modified
4. Update mapping logic in API implementation
5. Update `import.sql` if needed

## Troubleshooting

### Common Issues

**Build fails after OpenAPI changes**:
- Ensure `mvn package` runs successfully
- Check for OpenAPI validation errors
- Verify all generated interfaces are implemented

**Database connection issues**:
- Check PostgreSQL is running
- Verify connection properties in `application.properties`
- Ensure database and user exist

**Tests failing**:
- Verify H2 dependency is in test scope
- Check test configuration in `application-test.properties`

## Support

For issues or questions:
1. Check the Quarkus documentation: https://quarkus.io/guides/
2. Review OpenAPI specification: https://swagger.io/specification/
3. Consult Hibernate/Panache docs: https://quarkus.io/guides/hibernate-orm-panache

## Version Information

- **Application Version**: 1.0.0-SNAPSHOT
- **Java**: 21
- **Quarkus**: 3.33.2.SP1-redhat-00001
- **OpenAPI**: 3.0.3