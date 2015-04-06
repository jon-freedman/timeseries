package com.github.jonfreedman.timeseries.steps.helpers

import java.time.LocalDate

import com.github.jonfreedman.timeseries.Traverser
import cucumber.runtime.java.guice.ScenarioScoped

/**
 * @author jon
 */
@ScenarioScoped class LocalDateTimeSeriesHelper {
  var traverser: Traverser[LocalDate] = _
  var current: LocalDate = _
}
