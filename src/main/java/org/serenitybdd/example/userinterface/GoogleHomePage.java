package org.serenitybdd.example.userinterface;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

@DefaultUrl("https://www.google.com")
public class GoogleHomePage extends PageObject {
    public static Target inputSearch = Target.the("Search input")
            .located(By.tagName("textarea"));
    public static Target result = Target.the("Search result")
            .locatedBy("//h3");
}
