package org.serenitybdd.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.Wait;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.serenitybdd.example.questions.Item;
import org.serenitybdd.example.questions.TodoItems;
import org.serenitybdd.example.tasks.AddToDoItem;
import org.serenitybdd.example.tasks.InitializeTodoList;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.junit.Assert.assertThat;


public class AddToDoItemStep {

    @Given("that {actor} has an empty todo list")
    public void hasEmptyList(Actor actor) {
        actor.wasAbleTo(InitializeTodoList.withItems(List.of()));
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
        actor.remember("todoList", items);
        actor.wasAbleTo(InitializeTodoList.withItems(items));
    }
    
    @Then("his/her todo list should contain {itemList}")
    public void todoListShouldContainAllItems(List<String> expectedItems) {      
        // List<String> rememberItems = OnStage.theActorInTheSpotlight().recall("todoItems");
        // Assertions.assertThat(rememberItems).isNotEmpty();
        
         OnStage.theActorInTheSpotlight().attemptsTo(
                Wait.until(TodoItems.displayed(), Matchers.is(Matchers.not(Matchers.empty()))).forNoMoreThan(5).seconds(),
                Ensure.that(TodoItems.displayed()).containsElementsFrom(expectedItems)
        );
    }
}
