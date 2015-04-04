package com.github.jonfreedman.timeseries.steps.helpers

import java.lang

import com.github.jonfreedman.timeseries.interpolator.ValueInterpolator
import cucumber.runtime.java.guice.ScenarioScoped

/**
 * @author jon
 */
@ScenarioScoped class InterpolationHelper {
  var interpolator: ValueInterpolator[lang.Double] = _
  var interpValue: lang.Double = _
}
