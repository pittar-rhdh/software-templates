# gh-gpts — Software Templates for Backstage

Backstage Golden Path Templates in the `pittar-rhdh` GitHub org. Pure YAML — no build, test, or lint steps.

## Structure

```
templates/
  python/basic/       — Python app template (fetches skeleton + gitops, creates webhook, publishes two repos)
  java/quarkus/       — Quarkus template (fetches skeleton, publishes one repo, registers in catalog)
  basic/markdown/     — Markdown doc template (skeleton + catalog:register)
common/
  gitops-skeleton/    — Shared Kustomize overlays (dev, test, prod, cicd/eventlistener/pull-request-review)
software-templates.yaml  — Backstage Location collection
```

## Template execution order matters

Templates define explicit step ID references — a step must complete before another references its outputs:

- `python/basic`: `fetch` → `publish-repository` → `create-webhook` (consumes `publish-repository.remoteUrl`) → `fetch-gitops-common` → `fetch-gitops-python` → `publish-gitops-repository`
- `java/quarkus`: `fetch` → `publish-repository` → `create-webhook` → `register` (consumes `publish-repository.catalogInfoUrl`)
- `basic/markdown`: `fetch` → `publish-repository` → `register` (consumes `publish-repository.repoContentsUrl`)

Do not reorder steps or change step IDs without updating consumers.

## Key gotchas

- **All templates target `pittar-rhdh` org.** The `repoUrl` values are hardcoded with `owner=pittar-rhdh`. Moving the repo means updating every template's publish step.
- **Python template creates two repos:** the app repo (from `repoName` parameter) and a `repoName-gitops` repo. Java and markdown create only one.
- **Python vs Quarkus register step:** Python doesn't have a `catalog:register` step. Quarkus uses `steps['publish-repository'].output.catalogInfoUrl`. Markdown uses `steps['publish-repository'].output.repoContentsUrl` + a manual `catalogInfoPath: catalog-info.yaml`.
- **Quarkus has hardcoding:** `webhookUrl` is hardcoded to `https://webhook-${componentId}-cicd.apps.prime.pitt.ca` and `webhookSecret` defaults to `a6j2h82j`.
- **GitOps is Kustomize-based.** The `common/gitops-skeleton/overlays/cicd/` directory contains Tekton EventListener configs (eventlistener.yaml, triggers, bindings, trigger templates, GitHub secret). Python templates also include `templates/python/basic/gitops/cicd/` with additional trigger config.

## Adding a new template

1. Create `templates/<language>/<name>/template.yaml` following `scaffolder.backstage.io/v1beta3` schema.
2. Add the template target to `software-templates.yaml` under `spec.targets`.
3. If it publishes to GitHub, the `publish-repository` step's `repoUrl` must include `&owner=pittar-rhdh`.
4. If step A references outputs from step B (e.g. `remoteUrl`, `catalogInfoUrl`, `repoContentsUrl`), B must appear before A in the steps list.
