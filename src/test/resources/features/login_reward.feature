@search
Feature: Search on Google

  In order to avoid missing out on things I want to do
  As a forgetful person
  I want to be able to search for things on Google

  Scenario: Searching for a specific item
    Given I on the Google homepage
    When I search for "Cucumber BDD"
    Then I should see results related to "Cucumber BDD"
    When I click on the first result
    Then I should be on the Cucumber website