package org.serenitybdd.example.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.serenitybdd.example.userinterface.ToDoList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Question to check if the todo list contains all expected items
 * @Generated("copilot-agent v1.1")
 */
public class TodoItems implements Question<List<String>> {

    public static Question<List<String>> displayed() {
        return new TodoItems();
    }

    @Override
    public List<String> answeredBy(Actor actor) {
        List<WebElementFacade> elements = BrowseTheWeb.as(actor).findAll(ToDoList.ITEMS.getCssOrXPathSelector());
        return elements.stream()
                .map(WebElementFacade::getText)
                .collect(Collectors.toList());
    }
}
