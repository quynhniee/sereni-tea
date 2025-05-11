---
applyTo: '**'
---
Coding standards, domain knowledge, and preferences that AI should follow.

# ─────────────────────────────────────────────────────────────
#  Copilot Agent Instruction File  •  v1.1
#  Target stack: Java ▸ Cucumber ▸ Serenity-BDD ▸ Screenplay
# ─────────────────────────────────────────────────────────────
version: 1.1
agent_id: serenity-playwright-to-screenplay
description: |
  Convert MCP-Playwright runs into first-class Java Serenity Screenplay assets.
  The agent MUST:
    ➊ Read every Gherkin scenario.
    ➋ Execute with MCP-Playwright until **all steps pass**.
    ➌ Capture DOM locators and create/merge Screenplay PageObjects.
    ➍ Generate **one Step-Definition method per Gherkin step**, mapping
       @Given / @When / @Then tags exactly.
    ➎ Generate or update Screenplay Tasks and Questions for each action
       or assertion invoked by the Step-Definition methods.
    ➏ Place every file in its precise, pre-defined package path (see below).
  Any deviation from these locations is considered a failure.

# ─────────────────────────────────────────────────────────────
context:
  language: java
  test_runner: mcp-playwright
  frameworks:
    - cucumber-java
    - serenity-bdd
    - serenity-screenplay
  paths:
    features:            src/test/resources/features
    step_definitions:    src/test/java/org/serenitybdd/example/stepdefinitions
    screenplay_root:     src/main/java/org/serenitybdd/example
    actors:              ${screenplay_root}/actors
    tasks:               ${screenplay_root}/tasks
    questions:           ${screenplay_root}/questions
    userinterface:       ${screenplay_root}/userinterface

naming_conventions:
  # Derive class names deterministically so folders match packages
  step_definition_class: |
    "{feature_title_camel}StepDefinitions"     # e.g. “LoginStepDefinitions”
  task_class: |
    "{step_verb}{step_core_pascal}Task"        # e.g. “EnterCredentialsTask”
  question_class: |
    "{assertion_core_pascal}Question"          # e.g. “DisplayedUsernameQuestion”
  pageobject_class: |
    "{screen_name_pascal}Page"                 # e.g. “LoginPage”

# ─────────────────────────────────────────────────────────────
workflow:
  - id: parse-features
    description: |
      Parse *.feature files under **paths.features**.
      Persist an ordered list of:
        • Feature title
        • Scenario title
        • Step keyword (Given/When/Then/And/But)
        • Step text

  - id: discover-codebase
    description: |
      Scan compiled classes + source under **paths.screenplay_root**
      and **paths.step_definitions** to detect reusable components.

  - id: execute-with-playwright
    description: |
      Run each scenario via MCP-Playwright.
      IF ANY step fails, abort and report; do **not** generate code.

  - id: extract-locators
    description: |
      For each passed step, capture the locator actually used.
      Store selector → logical-name pairs for later PageObject creation.

  - id: create-or-update-pageobjects
    description: |
      For each logical screen, ensure one PageObject class exists in
      **paths.userinterface**.
      • Add new `Target` fields for any selectors not already present.
      • Do NOT rename or duplicate existing `Target` fields.

  - id: generate-step-definitions
    description: |
      For every Gherkin step, create (or update) a method in the proper
      Step-Definition class (see **naming_conventions.step_definition_class**).
      • Annotate with @Given/@When/@Then exactly as in the feature file.
      • Method body must delegate to a Task or Question (see below).
      • Save file to **paths.step_definitions**.

  - id: generate-tasks-and-questions
    description: |
      For each unique action (click, type, select…) create/merge a Task
      class under **paths.tasks**.
      For each unique verification, create/merge a Question under
      **paths.questions**.
      • Do NOT duplicate classes with identical intent.
      • Annotate each with @Step for Serenity reports.
      • All hard-coded selectors must reference PageObject `Target`s only.

  - id: write-code
    description: |
      Emit Java files, ensuring:
        • Package declarations match physical folders.
        • Each type has Javadoc + `@Generated("copilot-agent v1.1")`.
        • Imports are organised per **code_style.import_order**.

# ─────────────────────────────────────────────────────────────
rules:
  - Never place generated files outside the paths specified above.
  - Reuse existing code if semantics match; extend instead of duplicate.
  - Keep class names ≤ 3 words, PascalCase.
  - Methods ≤ 40 lines; factor helpers otherwise.
  - No “magic strings”: selectors belong only in PageObjects.
  - Treat THIS FILE as the single source of truth.

# ─────────────────────────────────────────────────────────────
code_style:
  indent: 4 spaces
  max_line_length: 120
  import_order: ["java", "javax", "org", "net", "com", "remaining"]

dependencies:
  - io.cucumber:cucumber-java
  - net.serenity-bdd:serenity-screenplay
  - com.microsoft.playwright:playwright

# ─────────────────────────────────────────────────────────────
self_checklist:
  - Have I produced one Step-Definition method for EVERY Gherkin step?
  - Are all new Tasks/Questions in the correct `tasks` or `questions` directory?
  - Do PageObjects live exclusively in **paths.userinterface** with proper `Target`s?
  - Does every generated file compile under the declared package?
  - Would another agent (or human) find the code base logically structured?
