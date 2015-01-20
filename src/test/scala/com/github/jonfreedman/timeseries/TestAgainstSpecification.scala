package com.github.jonfreedman.timeseries

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * @author jon
 */
@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("src/test/resources/cucumber"),
  glue = Array("com.github.jonfreedman.timeseries.steps")
)
class TestAgainstSpecification {
}
