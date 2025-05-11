package org.serenitybdd.example.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"org/serenitybdd/example/stepdefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        tags = "@todo"
)
public class TestRunner {
}
