package org.serenitybdd.example.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Displayed;
import org.serenitybdd.example.userinterface.GoogleHomePage;

import java.util.List;

public class SearchResult {
    public static Question<List<Boolean>> displayed() {
        return Displayed.ofEach(GoogleHomePage.result).describedAs("Search result");
    }
}
