package org.serenitybdd.example.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

import net.serenitybdd.screenplay.actors.OnStage;
import org.serenitybdd.example.userinterface.HomePage;

import java.util.List;

/**
 * A task that represents switching to a different actor's todo list.
 * In a real application, this would navigate to the appropriate list
 * or perform authentication as the specified user.
 */
public class SwitchUser implements Task {

    private final Actor targetActor;

    public SwitchUser(Actor targetActor) {
        this.targetActor = targetActor;
    }

    public static Performable to(Actor actor) {
        return Task.where("{0} switches to " + actor.getName() + "'s todo list",
                new SwitchUser(actor));
    }

    @Override
    @Step("{0} switches to #targetActor's todo list")
    public <T extends Actor> void performAs(T actor) {
        // In a real application with multiple user support, you would:
        // 1. Save current state
        // 2. Log out current user
        // 3. Log in as target user or navigate to their list
        
        // For this demo, we'll just refresh the page to simulate switching users
        actor.attemptsTo(
            Open.browserOn().the(HomePage.class)
        );
        
        // Remember we're now acting as the target actor
        actor.remember("currentActor", targetActor.getName());
        
        // Restore this actor's todo list
        if (targetActor.recall("todoList") != null) {
            List<String> items = targetActor.recall("todoList");
            actor.attemptsTo(InitializeTodoList.withItems(items));
        }
        
        // Put the target actor in the spotlight
        OnStage.theActorCalled(targetActor.getName());
    }
}
