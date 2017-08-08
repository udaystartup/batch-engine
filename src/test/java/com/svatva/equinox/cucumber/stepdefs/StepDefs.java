package com.svatva.equinox.cucumber.stepdefs;

import com.svatva.equinox.EbengineApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = EbengineApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
