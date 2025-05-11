package org.serenitybdd.example.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.Keys;
import org.serenitybdd.example.userinterface.GoogleHomePage;

public class Search {
    public static Performable withText(String text) {
        // Implementation for searching with the given text
        System.out.println("Searching with text: " + text);
        return Task.where("{0} searches with text: " + text,
                // Add the actual search implementation here
                Enter.keyValues(text).into(GoogleHomePage.inputSearch).thenHit(Keys.ENTER)
        );
    }

    public static Performable clickFirstResult() {
        // Implementation for clicking the first search result
        System.out.println("Clicking the first search result");
        return Task.where("{0} clicks the first search result",
                // Add the actual click implementation here
                Click.on(GoogleHomePage.result).afterWaitingUntilEnabled().withNoDelay()
        );
    }
}
