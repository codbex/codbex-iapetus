# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

Iapetus is a [codbex product](https://www.codbex.com/products/): **"Integrations Management for ETL and other enterprise integration patterns based on Apache Camel"** — providing an Integrations Modeler and Integrations Flows over hundreds of available adapters.

Technically, this repo is a thin packaging/assembly layer on top of the [Eclipse Dirigible](https://github.com/eclipse/dirigible) low-code platform. Almost all behavior comes from `org.eclipse.dirigible:dirigible-components-*` dependencies; this repo wires them together (selecting Camel/integration plus IDE and operations components), brands them, and adds a handful of custom UI components. There is very little custom Java here (just the Spring Boot bootstrap class).

**Three deliberate departures from the platform defaults**, all in the edition's own configuration:

- **Camel is the focus.** `engine-camel`, the Integrations (Karavan) modeler, the Camel menu and the `template-camel` aggregator (which carries the cron-route and http-route starters) are in; **no BPM tooling** - no BPMN editor, no Processes perspective, no BPM menus or templates. The Flowable engine itself does arrive on the classpath, because the client Java SDK exposes `org.eclipse.dirigible.sdk.bpm`: `api-modules-java` -> `api-bpm` -> `engine-bpm-flowable`. Excluding `api-bpm` builds and boots but leaves `sdk.bpm` broken the moment client code touches it, so the engine stays and only the tooling is left out.
- **No Intent Driven tooling.** There is no `engine-intent`, and `resources-builder` (the conversational Builder shell) is excluded from `group-ui`. `resources-inbox` is excluded for the same "nothing backs it" reason - the BPM task inbox has no BPM surface here.
- **Home is the Workbench IDE.** `DIRIGIBLE_HOME_URL=services/web/shell-ide/` in `dirigible.properties`, overriding the platform's 14.16.0+ default of `services/web/home/`. The launchpad stays reachable at `/services/web/home/`; only the `/` redirect changes. Consequence: upstream's `HomepageRedirectIT` asserts the platform default and cannot run here - it was dropped from the common suite, and the edition's own `HomePageIT` (Workbench welcome view on `/`) guards the override.

**Java support is full**: `engine-java` (in-process `javac` + the bean container), `data-store-java` (Java `@Entity` -> Hibernate), the `api-modules-java` SDK, `ide-java-lsp` (JDT.LS), `ide-java-debug` (the DAP bridge) and the Java / Java Debug views.

## Dirigible version

The version is pinned by `codbex-platform-parent` through its `dirigible.version` property; parent releases track Dirigible releases 1:1 (parent 14.17.0 -> Dirigible 14.17.0), so bumping the edition means bumping the parent. Bumps are not mechanical - Dirigible removes things: the OData engine was extracted in 14.16.0 (un-managing `com.codbex.olingo:olingo-odata2-lib`, which fails a version-less declaration at model-read time) and the AngularJS/TypeScript application templates went with it.

**The UI overrides under `components/ui/` are forks of upstream files** - diff them against the matching Dirigible module on every bump; they drift silently (translations must live in `i18n/<locale>/*.json`, the only folder `platform-core/extension-services/locales.js` scans).

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
