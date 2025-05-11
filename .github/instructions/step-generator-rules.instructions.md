---
applyTo: '**'
---
Coding standards, domain knowledge, and preferences that AI should follow.

# ─────────────────────────────────────────────────────────────
#  Step-Definition Generator Rules  •  v1.0
# ─────────────────────────────────────────────────────────────
purpose: >
  Provide exhaustive rules for turning every Gherkin step into a
  Java method inside the appropriate Step-Definition class.

references:
  # The agent should already know these from the main instruction file.
  step_definitions_dir: src/test/java/org/serenitybdd/example/stepdefinitions
  actor_param_type_class: org.serenitybdd.example.stepdefinitions.CucumberParameterTypes

# ─────────────────────────────────────────────────────────────
parameter_binding:
  # 1️⃣  ACTOR PRONOUNS
  - when: |
      Step text contains a personal pronoun referring to an actor
      (he, she, him, her, his, her, they, their, them, it).
    then:
      • Confirm that a Cucumber @ParameterType(name="actor") is declared
        in **actor_param_type_class** (or create it if missing).
      • Generate the method signature with a first parameter of
        `Actor actor` (imported from `net.serenitybdd.screenplay.Actor`).
      • Replace the pronoun in the annotation pattern with `{actor}`.
      • Use the form **“his/her”** instead of a single-gender token so
        Serenity can match both.  
        e.g.  
        ```gherkin
        When he adds 'Buy milk' to his list
        ```  
        ⭢  
        ```java
        @When("{actor} adds {string} to his/her list")
        public void adds_item_to_list(Actor actor, String item) { … }
        ```

  # 2️⃣  LIST-OF-VALUES (comma-separated)
  - when: |
      Step text contains a comma-separated list such as
      `"Buy some milk, Walk the dog, Buy some cereal"`
      (optionally with an "and" before the last item).
    then:
      • Treat the parameter as `List<String>` in the method signature.
      • In the annotation pattern, replace the list with `{stringList}`.
      • Ensure a custom `@ParameterType(name="stringList")`
        exists that converts the raw step text into `List<String>`.
      • Example converter (for reference only, do NOT inline into
        Step-Definition):  
        ```java
        @ParameterType(".*")
        public List<String> stringList(String csv) {
            return Arrays.stream(csv.split("\\s*,\\s*|\\s+and\\s+"))
                         .map(String::trim)
                         .filter(s -> !s.isEmpty())
                         .collect(Collectors.toList());
        }
        ```

  # 3️⃣  DEFAULT STRINGS
  - when: Any other quoted content
    then: Use `{string}` → `String` as usual.

# ─────────────────────────────────────────────────────────────
method_naming:
  rule: >
    snake_case the step text, strip Cucumber keywords and parameters,
    keep ≤ 60 characters. Example:  
    “When {actor} adds {string} to his/her list”  
    → `adds_item_to_list`.

# ─────────────────────────────────────────────────────────────
examples:
  - gherkin: |
      When he purchases "5" items
    java: |
      @When("{actor} purchases {string} items")
      public void purchases_items(Actor actor, String quantity) { … }

  - gherkin: |
      Then she should see Buy some milk, Walk the dog, Buy some cereal in her list
    java: |
      @Then("{actor} should see {stringList} in his/her list")
      public void should_see_items_in_list(Actor actor, List<String> items) { … }

# ─────────────────────────────────────────────────────────────
self_checklist:
  - Does every generated method include an `Actor` parameter when `{actor}` appears?
  - Have all pronouns been normalised to “his/her” in the annotation?
  - Are comma-separated lists mapped to `List<String>` with `{stringList}`?
  - Do generated method names follow the `snake_case` rule and compile?
