package org.serenitybdd.example.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import org.serenitybdd.example.userinterface.GoogleHomePage;

public class Start {

    public static Performable on_the_google_homepage() {
        // Implementation for starting on the Google homepage
        System.out.println("Starting on the Google homepage");
        return Task.where("{0} starts on the Google homepage",
                Open.browserOn().the(GoogleHomePage.class)
        );
    }
}
