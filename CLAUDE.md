# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

Iapetus is a [codbex product](https://www.codbex.com/products/): **"Integrations Management for ETL and other enterprise integration patterns based on Apache Camel"** — providing an Integrations Modeler and Integrations Flows over hundreds of available adapters.

Technically, this repo is a thin packaging/assembly layer on top of the [Eclipse Dirigible](https://github.com/eclipse/dirigible) low-code platform. Almost all behavior comes from `org.eclipse.dirigible:dirigible-components-*` dependencies; this repo wires them together (selecting Camel/integration plus IDE and operations components), brands them, and adds a handful of custom UI components. There is very little custom Java here (just the Spring Boot bootstrap class).

## Module layout

Maven multi-module reactor (parent `pom.xml`, packaging `pom`):

- **`application/`** — the deployable Spring Boot app (`codbex-iapetus-application`). Its `pom.xml` is the real assembly: it selects which Dirigible component groups/modules are bundled (core, security providers, engines incl. `dirigible-components-engine-camel`, IDE backend/UI, editors, perspectives, views, templates). The entrypoint is `IapetusApplication.java`. Contains the `Dockerfile`.
- **`components/`** — custom Dirigible content modules (`ui/menu-help`, `ui/view-welcome`). These are **not Java** — they are Dirigible artifacts (`.extension`, `.js`, `.html`, `project.json`) under `src/main/resources/META-INF/dirigible/<name>/`, packaged as jars and contributed to the runtime classpath.
- **`branding/`** — logo, favicon, branding `project.json` (also Dirigible content, no Java).
- **`integration-tests/`** — Selenium/UI + API integration tests. The custom test classes here mostly *select* and run suites of tests that live in upstream Dirigible (`org.eclipse.dirigible.integration.tests.*`). See `DirigibleCommonTestSuiteIT`.

The parent `com.codbex.platform:codbex-platform-parent` (note version in `pom.xml`) defines dependency versions, plugin config, and the build **profiles** below. Dirigible/platform version bumps are the dominant change in git history.

## Build & run commands

All run from the repo root unless noted. Profiles come from the platform parent, not this repo.

```shell
# Fast build (skips tests, formatting, etc.) — produces application/target/codbex-iapetus-*.jar
mvn -T 1C clean install -P quick-build

# Run the standalone jar (requires the --add-opens flags; serves on port 80)
java --add-opens=java.base/java.lang=ALL-UNNAMED \
     --add-opens=java.base/java.lang.reflect=ALL-UNNAMED \
     --add-opens=java.base/java.nio=ALL-UNNAMED \
     -jar application/target/codbex-iapetus-*.jar
# debug: add -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000

# Docker (build jar first, then from application/): docker build . --tag <image>
```

Tests and formatting (each is a profile, run with `mvn clean install -P <profile>`):

| Command | Purpose |
|---|---|
| `mvn clean install -P unit-tests` | unit tests only |
| `mvn clean install -P integration-tests` | integration tests (Selenium UI + API) |
| `mvn clean install -P tests` | all tests |
| `mvn verify -P format` | apply code formatting — run before committing Java changes |

Run a single integration test class:
```shell
mvn clean install -P integration-tests -pl integration-tests -Dtest=HomePageIT -Dit.test=HomePageIT
```

## Runtime configuration (Spring profiles)

The app is profile-driven. `application.properties` activates `common,app-default` by default; `app-default` sets `server.port=80`. To enable a Dirigible auth/integration provider you must add `common` and `app-default` explicitly alongside it, e.g. `SPRING_PROFILES_ACTIVE=common,snowflake,app-default`. Security providers wired in `application/pom.xml`: basic, keycloak, cognito, snowflake, client-registration.

The Spring Boot app scans `org.eclipse.dirigible` (not `com.codbex`) and explicitly excludes the default JDBC/JPA auto-configurations — Dirigible manages its own datasources.

## Access

App at `http://localhost:80`, default login `admin` / `admin`. REST API / Swagger UI at `http://localhost/swagger-ui/index.html`.

## Conventions

- Java source files carry the EPL-2.0 license header (see any existing `.java`); preserve it on new files.
- When adding/removing bundled platform capabilities, edit the dependency list in `application/pom.xml` — that file *is* the product definition. Custom UI content goes under `components/` as a new Dirigible content module (mirror `components/ui/view-welcome` structure) and is registered via its `project.json` and `.extension` files.
