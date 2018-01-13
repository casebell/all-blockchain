package io.iansoft.blockchain.cucumber.stepdefs;

import io.iansoft.blockchain.BlockchainApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = BlockchainApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
