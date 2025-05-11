package org.serenitybdd.example.userinterface;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;

@DefaultUrl("https://todomvc.com/examples/angular/dist/browser/#/all")
public class HomePage extends PageObject {
    public static ToDoList toDoList;
}
