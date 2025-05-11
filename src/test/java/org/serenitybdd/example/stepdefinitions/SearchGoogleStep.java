package org.serenitybdd.example.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_old.Ac;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.questions.page.TheWebPage;
import org.hamcrest.Matchers;
import org.serenitybdd.example.questions.SearchResult;
import org.serenitybdd.example.tasks.Search;
import org.serenitybdd.example.tasks.Start;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class SearchGoogleStep {
//    @Before
//    public void set_the_stage() {
//        // Initialize the stage or any necessary setup
//        // This could include setting up the actor or the environment
//        OnStage.setTheStage(new OnlineCast());
//    }
    // customize Cucumber expressions
//    @ParameterType(".*")
//    public Actor actor(String name) {
//        return OnStage.theActorCalled(name);
//    }

    @Given("{actor} on the Google homepage")
    public void on_the_google_homepage(Actor actor) {
        // Code to navigate to the Google homepage
        // This could involve using a web driver or a specific method from your framework
        actor.wasAbleTo(Start.on_the_google_homepage());
    }


    @When("{actor} search for {string}")
    public void iSearchFor(Actor actor, String query) {
        actor.attemptsTo(
                Search.withText(query)
        );
    }

    @Then("{actor} should see results related to {string}")
    public void iShouldSeeResultsRelatedTo(Actor actor, String query) {
        actor.should(seeThat(
                SearchResult.displayed(),
                Matchers.contains(true)
        ));
    }

    @When("{actor} click on the first result")
    public void iClickOnTheFirstResult(Actor actor) {
        actor.attemptsTo(
                Search.clickFirstResult()
        );
    }

    @Then("{actor} should be on the Cucumber website")
    public void iShouldBeOnTheCucumberWebsite(Actor actor) {
        actor.should(seeThat(
                TheWebPage.currentUrl().describedAs("the current URL"),
                Matchers.equalTo("https://cucumber.io/")
        ));
    }
}
