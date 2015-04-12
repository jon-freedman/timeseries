package com.github.jonfreedman.timeseries.steps.helpers

import java.time.LocalDate
import java.util

import com.github.jonfreedman.timeseries.calculation.TemporalCalculator.Observation
import cucumber.runtime.java.guice.ScenarioScoped

/**
 * @author jon
 */
@ScenarioScoped class CalculationHelper {
  var bigDecimalResult: util.Map[String, java.math.BigDecimal] = _
  var observationResult: util.Map[String, Observation[LocalDate, java.math.BigDecimal]] = _
}
