package com.kyrylocodefirst.e2etest.configuration;

import org.springframework.boot.test.context.SpringBootTest;

import com.kyrylocodefirst.KyryloCodeFirstApplication;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = KyryloCodeFirstApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration {
}
