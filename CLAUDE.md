# ShowcaseApp — Project Configuration

## Overview

Android app demonstrating best engineering practices for potential stakeholders (HRs, engineering managers). Built using a CI/CD pipeline with AI agents handling code generation, PR reviews, and task management.

- **Package:** `com.prokopov.showcaseapp`
- **Repo:** `git@github.com:AProkopov/ShowcaseApp.git`
- **Branch:** `master`

## Architecture

- **Modules:** Multi-module (planned — currently single `:app` module)
  - `app/` — entry point only
  - `core/core-ui/` — design system, theme, shared composables
  - `core/core-data/` — repository interfaces, domain models
  - `core/core-network/` — Retrofit/Ktor, API definitions
  - `core/core-testing/` — test utilities, fakes
  - `feature/feature-home/` — home/dashboard screen
  - `feature/feature-settings/` — settings with DataStore
  - `feature/feature-showcase/` — catalog of pattern demos
  - `build-logic/convention/` — shared Gradle convention plugins
- **Navigation:** Compose Navigation with type-safe routes, bottom nav (Home, Showcase, Settings)
- **DI:** Hilt across all modules

## Build Commands

```bash
./gradlew assembleDebug       # Build debug APK
./gradlew test                # Run unit tests
./gradlew lint                # Run Android Lint
./gradlew detekt              # Run Detekt static analysis (after Phase 2)
./gradlew ktlintCheck         # Run ktlint formatting check (after Phase 2)
```

## Current Dependencies

From `gradle/libs.versions.toml`:
- AGP 8.11.2, Kotlin 2.0.21
- Compose BOM 2024.09.00, Material3
- AndroidX Core KTX 1.10.1, Lifecycle 2.6.1, Activity Compose 1.8.0
- JUnit 4.13.2, Espresso 3.5.1

## Workflow

All changes go through: branch -> code -> PR -> CI checks -> review -> squash-merge to master.

GitHub Projects board tracks all work (link TBD after Phase 1 setup).
