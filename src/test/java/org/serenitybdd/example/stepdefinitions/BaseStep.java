package org.serenitybdd.example.stepdefinitions;

import java.util.List;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class BaseStep {
    @Before
    public void set_the_stage() {
        // Initialize the stage or any necessary setup
        // This could include setting up the actor or the environment
        OnStage.setTheStage(new OnlineCast());
    }
    // customize Cucumber expressions
    @ParameterType(".*")
    public Actor actor(String name) {
        return OnStage.theActorCalled(name);
    }

    @ParameterType(".*")
    public List<String> itemList(String items) {
        return List.of(items.split(",\\s*"));
    }
}
