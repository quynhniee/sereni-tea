package org.serenitybdd.example.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.serenitybdd.example.userinterface.ToDoList;

public class Item {
    public static Question<String> displayed() {
        return Text.of(ToDoList.ITEM).describedAs("the item displayed");
    }
}
