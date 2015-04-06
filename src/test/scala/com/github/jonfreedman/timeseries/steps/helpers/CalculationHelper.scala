package com.github.jonfreedman.timeseries.steps.helpers

import java.util

import cucumber.runtime.java.guice.ScenarioScoped

/**
 * @author jon
 */
@ScenarioScoped class CalculationHelper {
  var bigDecimalResult: util.Map[String, java.math.BigDecimal] = _
}
