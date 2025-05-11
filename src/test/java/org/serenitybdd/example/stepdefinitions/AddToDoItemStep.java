package org.serenitybdd.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.Wait;

import org.hamcrest.Matchers;
import org.serenitybdd.example.questions.Item;
import org.serenitybdd.example.questions.TodoItems;
import org.serenitybdd.example.tasks.AddToDoItem;
import org.serenitybdd.example.tasks.InitializeTodoList;
import org.serenitybdd.example.tasks.SwitchUser;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

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
    
    @Then("{actor}'s todo list should contain {itemList}")
    public void actors_todo_list_should_contain(Actor actor, List<String> expectedItems) {
        // First switch to the actor's todo list if needed
        if (!OnStage.theActorInTheSpotlight().equals(actor)) {
            OnStage.theActorInTheSpotlight().attemptsTo(SwitchUser.to(actor));
        }
        
        // Now verify the items in the list
        OnStage.theActorInTheSpotlight().should(seeThat(
                TodoItems.displayed(), Matchers.containsInAnyOrder(expectedItems.toArray())
        ).orComplainWith(Error.class, "The expected items were not found in " + actor.getName() + "'s list"));
    }
}
