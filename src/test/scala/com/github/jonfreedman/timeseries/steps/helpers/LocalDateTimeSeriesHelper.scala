package com.github.jonfreedman.timeseries.steps.helpers

import java.time.LocalDate

import com.github.jonfreedman.timeseries.localdate.LocalDateTraverser
import cucumber.runtime.java.guice.ScenarioScoped

/**
 * @author jon
 */
@ScenarioScoped class LocalDateTimeSeriesHelper {
  var traverser: LocalDateTraverser = _
  var current: LocalDate = _
}
