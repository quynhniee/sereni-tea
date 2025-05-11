package org.serenitybdd.example.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.Keys;
import org.serenitybdd.example.userinterface.ToDoList;

public class AddToDoItem {
    public static Task called(String thingToDo) {
        return Task.where("{0} adds a todo item called: " + thingToDo,
                Enter.keyValues(thingToDo).into(ToDoList.WHAT_NEEDS_TO_BE_DONE).thenHit(Keys.ENTER));
    }
}
