# Basic Quarkus Application Template

This Backstage template creates a basic Quarkus application with full DevOps pipeline integration for the `pittar-rhdh` organization.

## What This Template Creates

- **Quarkus Application**: Modern Java application with REST endpoints
- **GitOps Integration**: Complete Kubernetes deployment configuration
- **CI/CD Pipeline**: Tekton-based pipelines with GitHub webhook triggers
- **Backstage Catalog Registration**: Automatic entity registration in the Backstage catalog
- **Development Environment**: Pre-configured VS Code settings and devfile

## Generated Structure

```
your-repo/
├── src/main/java/.../         # Java source code
├── src/test/.../               # Test files
├── gitops/                     # Kustomize overlays for dev/test/prod
├── argocd/                     # ArgoCD application definitions
├── catalog-info.yaml          # Backstage catalog entity
├── pom.xml                    # Maven project configuration
├── Containerfile              # Container build configuration
├── .devfile.yaml              # Dev Containers configuration
└── mkdocs.yaml                # Documentation configuration
```

## Parameters

### Repository Information
- **Repository Name**: GitHub repository name (must follow GitHub naming conventions)

### Component Information
- **Project ID**: Unique lowercase project identifier (used as Maven artifact ID)
- **Name**: Human-readable component name
- **Owner**: Backstage group or user who owns this component
- **Group ID**: Maven group ID (default: `ca.pitt.demo.idp`)
- **Description**: Project description
- **Webhook Secret**: Secret for GitHub webhook authentication (default: `a6j2h82j`)

## Template Steps

1. **Fetch Template**: Downloads the skeleton application structure
2. **Publish Repository**: Creates the GitHub repository in `pittar-rhdh` org
3. **Create Webhook**: Sets up GitHub webhooks for CI/CD triggers
4. **Register in Catalog**: Registers the component in Backstage catalog

## Output

After template execution, you will have:
- A GitHub repository URL
- A Backstage catalog entity reference
- Configured webhooks for automated deployments

## Requirements

- Quarkus 3.23.3
- Java 21
- PostgreSQL database (configured for production)
- H2 database (for testing)

## Next Steps

After repository creation:
1. Clone the repository
2. Review and customize the application code
3. Set up database credentials in your deployment environment
4. Monitor deployments through the ArgoCD applications