package org.serenitybdd.example.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import org.openqa.selenium.Keys;
import org.serenitybdd.example.userinterface.HomePage;
import org.serenitybdd.example.userinterface.ToDoList;

import java.util.Arrays;
import java.util.List;

/**
 * Task to initialize a todo list with multiple items
 * @Generated("copilot-agent v1.1")
 */
public class InitializeTodoList implements Task {

    private final List<String> items;

    public InitializeTodoList(List<String> items) {
        this.items = items;
    }

    public static Performable withItems(List<String> items) {
        return Task.where("{0} starts with a todo list containing: " + String.join(", ", items),
                new InitializeTodoList(items));
    }

    @Override
    @Step("{0} initializes todo list with #items")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.browserOn().the(HomePage.class)
        );
        
        // Add each item to the list
        for (String item : items) {
            actor.attemptsTo(
                    Enter.keyValues(item).into(ToDoList.WHAT_NEEDS_TO_BE_DONE).thenHit(Keys.ENTER)
            );
        }
    }
}
