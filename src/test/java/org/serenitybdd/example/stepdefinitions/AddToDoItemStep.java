package org.serenitybdd.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import org.hamcrest.Matchers;
import org.serenitybdd.example.questions.Item;
import org.serenitybdd.example.questions.TodoItems;
import org.serenitybdd.example.tasks.AddToDoItem;
import org.serenitybdd.example.tasks.InitializeTodoList;
import org.serenitybdd.example.tasks.Start;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;


public class AddToDoItemStep {

    @Given("that {actor} has an empty todo list")
    public void hasEmptyList(Actor actor) {
        actor.wasAbleTo(InitializeTodoList.withItems(List.of()));

//        System.out.println("Starting with an empty todo list");
//        // Implementation for ensuring the list is empty
//        actor.wasAbleTo(Start.withAnEmptyTodoList());
    }

    @When("{actor} adds {string} to his/her list")
    public void add_to_list(Actor actor, String item) {
        // Implementation for adding an item to the list
        System.out.println("Adding item: " + item);
        actor.attemptsTo(AddToDoItem.called(item));
    }

    @Then("{string} should be recorded in his/her list")
    public void itemShouldBeRecordedInList(String item) {
        OnStage.theActorInTheSpotlight().should(seeThat(
                Item.displayed(), Matchers.equalTo(item))
                .orComplainWith(Error.class, "The item was not found in the list")
        );
    }
    
    @Given("{actor} has a todo list containing {itemList}")
    public void actorHasATodoListContainingItems(Actor actor, List<String> items) {
        actor.wasAbleTo(InitializeTodoList.withItems(items));
    }
    
    @Then("{actor} todo list should contain {itemList}")
    public void todoListShouldContainAllItems(Actor actor, List<String> expectedItems) {        
        List<String> todoItems = TodoItems.displayed().answeredBy(actor);
        
        // Check that all expected items are in the list
        for (String expectedItem : expectedItems) {
            actor.should(
                seeThat("Todo list contains " + expectedItem, 
                        a -> todoItems.contains(expectedItem))
                    .orComplainWith(Error.class, "The item was not found in the list: " + expectedItem)
            );
        }
    }
}
