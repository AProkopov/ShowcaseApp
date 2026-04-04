# ShowcaseApp

[![Android Build](https://github.com/AProkopov/ShowcaseApp/actions/workflows/android-build.yml/badge.svg)](https://github.com/AProkopov/ShowcaseApp/actions/workflows/android-build.yml)
[![Code Quality](https://github.com/AProkopov/ShowcaseApp/actions/workflows/code-quality.yml/badge.svg)](https://github.com/AProkopov/ShowcaseApp/actions/workflows/code-quality.yml)

Android app demonstrating best engineering practices — architecture, testing, CI/CD, and AI-assisted development workflow.

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material3
- **Architecture:** MVVM, single-activity, multi-module (planned)
- **DI:** Hilt (planned)
- **Async:** Coroutines + Flow
- **Build:** Gradle Kotlin DSL, version catalogs
- **CI/CD:** GitHub Actions (build, test, lint, Detekt, ktlint)
- **Code Quality:** Detekt, ktlint

## Build

```bash
./gradlew assembleDebug   # Build debug APK
./gradlew test            # Run unit tests
./gradlew lint            # Android Lint
./gradlew detekt          # Detekt static analysis
./gradlew ktlintCheck     # ktlint formatting check
```

## Project Structure

```
ShowcaseApp/
├── app/                   # Application module
├── gradle/
│   └── libs.versions.toml # Dependency version catalog
├── .github/
│   ├── workflows/         # CI/CD pipelines
│   └── ISSUE_TEMPLATE/    # Issue templates
├── detekt.yml             # Detekt configuration
└── .editorconfig          # Editor and ktlint configuration
```

## Development Workflow

All changes follow: branch → code → PR → CI checks → review → squash-merge to master.

Tracked on [GitHub Projects board](https://github.com/users/AProkopov/projects/1).
