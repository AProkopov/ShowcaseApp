# ShowcaseApp + AI Infrastructure Roadmap

Started: 2026-04-04

## Phase 0: Foundation
- [x] Install `gh` CLI
- [x] Create global `~/CLAUDE.md`
- [x] Create project `ShowcaseApp/CLAUDE.md`
- [x] Set up Claude memory system
- [x] Create this roadmap (repo + memory)
- [x] Authenticate `gh` CLI (`gh auth login`)

## Phase 1: GitHub Workflow
- [x] Configure branch protection on `master`
- [x] Create GitHub Projects board (Backlog -> Ready -> In Progress -> In Review -> Done)
- [x] Create labels: `infra`, `feature`, `bug`, `ci-cd`, `agent`, `architecture`, `documentation`
- [x] Create issue templates (feature request, bug report, task)
- [x] Create PR template
- [x] Create milestone issues for Phases 2-6 (#1-#5)
- [x] Verify end-to-end: branch -> PR -> review -> merge (PR #6)

## Phase 2: CI/CD Pipeline
- [x] Android build workflow (assembleDebug, test, lint)
- [x] Add Detekt + ktlint for code quality
- [x] Code quality workflow
- [x] Wire status checks to branch protection (5 required checks)
- [x] README with build badge

## Phase 3: First AI Agents
- [x] Create `android-dev` plugin scaffold
- [x] Build `android-code-writer` agent
- [x] Build `android-pr-reviewer` agent
- [x] Build `/new-feature` command
- [x] Build `/review-pr` command
- [x] Test end-to-end: code-writer -> PR -> reviewer -> fix -> merge (PR #9, issue #8)

## Phase 4: ShowcaseApp Architecture (interleave with Phase 5)
- [ ] Multi-module setup with convention plugins (`build-logic/`)
- [ ] Hilt DI across modules
- [ ] Type-safe Compose Navigation + bottom nav
- [ ] Feature: Design system (core-ui)
- [ ] Feature: Home screen
- [ ] Feature: Settings (theme toggle, DataStore)
- [ ] Feature: Showcase catalog
- [ ] Feature: Network layer (GitHub API)
- [ ] Testing: ViewModel unit tests, Compose UI tests, fakes

## Phase 5: Advanced Agents (interleave with Phase 4)
- [ ] Auto-merger agent
- [ ] Task tracker agent
- [ ] Design-to-code agent (Figma screenshots -> Compose)
- [ ] `/sprint` orchestrator command

## Phase 6: Integration & Polish
- [ ] Extract `android-dev` plugin to separate repo
- [ ] ShowcaseApp polish (icon, splash, tech stack screen, process screen)
- [ ] Create template repository
- [ ] Retrospective + CLAUDE.md refinement

---

**Estimated total: 3-5 weeks part-time**
