package com.kyrylocodefirst.e2etest;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = "src/test/java/resources/features",
    glue = {"com.kyrylocodefirst.e2etest.features", "com.kyrylocodefirst.e2etest.configuration"},
    publish = true
    )
public class PriceControllerE2ETest {
}
