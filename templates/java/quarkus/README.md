# Quarkus Golden Path Template

This is a **Backstage Golden Path Template** that provisions a complete Quarkus application with standardized DevOps infrastructure for the `pittar-rhdh` organization. Golden Path Templates provide opinionated, production-ready scaffolding that follows organizational best practices and standards.

## What This Template Creates

This template provisions a complete, production-ready Quarkus application stack:

- **Quarkus Application**: Modern Java 21 application with RESTful endpoints and database integration
- **GitOps Integration**: Kustomize-based deployment configurations for dev/test/prod environments
- **CI/CD Pipeline**: Tekton pipelines with GitHub webhook automation for continuous delivery
- **Backstage Catalog Registration**: Automatic entity registration enabling discovery and governance
- **Development Environment**: Pre-configured development container and VS Code integration
- **Infrastructure as Code**: ArgoCD application definitions for GitOps deployments

## Generated Repository Structure

The template creates a standardized repository structure following organizational conventions:

```
your-repo/
├── src/main/java/.../         # Quarkus application source code
├── src/test/.../               # Test suites and integration tests
├── gitops/                     # Kustomize overlays (dev/test/prod/cicd)
│   ├── overlays/dev/           # Development environment config
│   ├── overlays/test/          # Testing environment config
│   ├── overlays/prod/          # Production environment config
│   └── overlays/cicd/          # CI/CD Tekton configurations
├── argocd/                     # ArgoCD application definitions
├── catalog-info.yaml          # Backstage catalog entity definition
├── pom.xml                    # Maven project and dependencies
├── Containerfile              # OCI container build specification
├── .devfile.yaml              # Development container configuration
└── mkdocs.yaml                # Technical documentation setup
```

## Template Parameters

### Repository Configuration
- **Repository Name**: GitHub repository name following organizational naming conventions
  - Must be lowercase, kebab-case
  - Examples: `user-service`, `payment-processor`

### Component Metadata
- **Project ID**: Unique lowercase identifier used as Maven artifact ID
- **Name**: Human-readable component name for display in Backstage
- **Owner**: Backstage group or user entity responsible for this component
- **Group ID**: Maven group ID (default: `ca.pitt.demo.idp`)
- **Description**: Comprehensive project description for catalog metadata

### Infrastructure Configuration
- **Webhook Secret**: Secret for GitHub webhook authentication
  - Default: `a6j2h82j` (should be customized for production)
  - Used to secure webhook communications between GitHub and Tekton

## Template Execution Steps

The template executes in a specific order to ensure dependencies are satisfied:

1. **Fetch Skeleton Application**
   - Downloads base Quarkus application structure with REST endpoints
   - Includes sample entities, repositories, and resource classes
   - Sets up Maven configuration with required dependencies

2. **Publish Repository to GitHub**
   - Creates repository in `pittar-rhdh` GitHub organization
   - Initializes with generated application code
   - Sets up default branch protection and repository settings

3. **Create Webhook Infrastructure**
   - Configures GitHub webhooks for CI/CD triggers
   - Hardcoded webhook URL: `https://webhook-${componentId}-cicd.apps.prime.pitt.ca`
   - Enables automated pipeline execution on repository events

4. **Register in Backstage Catalog**
   - Creates `catalog-info.yaml` entity definition
   - Enables component discovery in Backstage
   - Provides ownership tracking and governance integration

## Template Execution Output

Upon successful completion, you will receive:

- **Repository URL**: Direct link to the created GitHub repository
- **Catalog Entity Reference**: Backstage entity identifier for discovery
- **Webhook Configuration**: Automated CI/CD trigger setup
- **ArgoCD Application**: GitOps application definition for deployment automation

## Post-Template Workflow

1. **Repository Initialization**
   ```bash
   git clone <repository-url>
   cd <repository-name>
   ```

2. **Development Setup**
   ```bash
   # Using development container
   code .  # Opens in VS Code with dev container
   ```

3. **Application Development**
   - Customize Quarkus application code in `src/main/java/`
   - Add business logic, entities, and REST endpoints
   - Update database configurations as needed
   - Extend test suites for new functionality

4. **Deployment Verification**
   - Monitor ArgoCD applications for deployment status
   - Validate CI/CD pipeline execution
   - Verify webhook functionality and deployment triggers

## Technical Requirements

### Runtime Dependencies
- **Quarkus Framework**: Version 3.23.3
- **Java**: Version 21 (LTS)
- **PostgreSQL**: Primary database for production environments
- **H2 Database**: In-memory database for development and testing

### Infrastructure Prerequisites
- Access to `pittar-rhdh` GitHub organization
- Tekton CI/CD cluster with webhook receivers
- ArgoCD instance for GitOps deployments
- Backstage instance for catalog integration

## Golden Path Benefits

This template represents the **golden path** for Quarkus application development by providing:

- **Standardization**: Consistent project structure and configuration across all Quarkus services
- **Best Practices**: Pre-configured security, testing, and deployment patterns
- **Automation**: Full CI/CD pipeline with minimal manual intervention
- **Governance**: Integrated Backstage catalog for compliance and discovery
- **Developer Experience**: Pre-configured development environment with tooling
- **Operational Excellence**: GitOps-based deployment with environment isolation

## Customization

While this template provides opinionated defaults, you can customize:

- **Application Logic**: Modify source code, entities, and business logic
- **Database Configuration**: Adapt connection settings and schema
- **Deployment Configuration**: Adjust Kustomize overlays for specific environments
- **Pipeline Steps**: Extend Tekton pipelines for additional build/deploy stages
- **Documentation**: Update MkDocs configuration and add technical documentation

## Next Steps

After repository creation and initial setup:

1. **Code Development**
   - Implement business logic in Quarkus application
   - Add database entities and repository interfaces
   - Create REST endpoints and validation logic
   - Write comprehensive test suites

2. **Environment Configuration**
   - Configure database credentials in deployment secrets
   - Adjust environment-specific settings in Kustomize overlays
   - Validate webhook connectivity and security settings

3. **Deployment and Monitoring**
   - Monitor ArgoCD applications for deployment status
   - Verify Tekton pipeline execution
   - Set up application monitoring and logging
   - Configure alerting for production environments

4. **Backstage Integration**
   - Update catalog entity with additional metadata
   - Add documentation links and owner information
   - Configure ownership groups and access policies

This golden path template ensures your Quarkus application follows organizational standards while providing a robust foundation for production deployment and operations.