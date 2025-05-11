package org.serenitybdd.example.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.questions.Text;

import org.serenitybdd.example.userinterface.ToDoList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Question to check if the todo list contains all expected items
 * @Generated("copilot-agent v1.1")
 */
public class TodoItems {
    public static Question<Collection<String>> displayed() {
        return Text.ofEach(ToDoList.ITEM).describedAs("the items displayed");
    }
}
