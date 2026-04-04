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
- [ ] Create GitHub Projects board (Backlog -> Ready -> In Progress -> In Review -> Done)
- [x] Create labels: `infra`, `feature`, `bug`, `ci-cd`, `agent`, `architecture`, `documentation`
- [x] Create issue templates (feature request, bug report, task)
- [x] Create PR template
- [x] Create milestone issues for Phases 2-6 (#1-#5)
- [ ] Verify end-to-end: branch -> PR -> review -> merge

## Phase 2: CI/CD Pipeline
- [ ] Android build workflow (assembleDebug, test, lint)
- [ ] Add Detekt + ktlint for code quality
- [ ] Code quality workflow
- [ ] Wire status checks to branch protection
- [ ] README with build badge

## Phase 3: First AI Agents
- [ ] Create `android-dev` plugin scaffold
- [ ] Build `android-code-writer` agent
- [ ] Build `android-pr-reviewer` agent
- [ ] Build `/new-feature` command
- [ ] Build `/review-pr` command
- [ ] Test end-to-end: `/new-feature` produces a compilable PR with review

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
