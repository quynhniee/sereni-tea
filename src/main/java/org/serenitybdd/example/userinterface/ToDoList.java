package org.serenitybdd.example.userinterface;

import net.serenitybdd.screenplay.targets.Target;

public class ToDoList {
    public static Target WHAT_NEEDS_TO_BE_DONE = Target.the("What needs to be done? input")
            .locatedBy("//input[@placeholder='What needs to be done?']");
    public static Target ITEM = Target.the("Todo item")
            .locatedBy(".view label");
    public static Target ITEMS = Target.the("All todo items")
            .locatedBy(".todo-list li .view label");
}
