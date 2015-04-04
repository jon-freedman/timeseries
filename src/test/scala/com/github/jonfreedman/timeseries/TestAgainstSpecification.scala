package com.github.jonfreedman.timeseries

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * @author jon
 */
@RunWith(classOf[Cucumber])
@CucumberOptions(
  plugin = Array("json:target/cucumber.json"),
  features = Array("src/test/resources/cucumber"),
  glue = Array("com.github.jonfreedman.timeseries.steps"),
  tags = Array("~@ignore")
)
class TestAgainstSpecification