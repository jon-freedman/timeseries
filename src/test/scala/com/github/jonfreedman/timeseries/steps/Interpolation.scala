package com.github.jonfreedman.timeseries.steps

import java.lang

import com.github.jonfreedman.timeseries.interpolator.{FlatFillInterpolator, LinearInterpolator, ZeroValueInterpolator}
import com.github.jonfreedman.timeseries.steps.helpers.InterpolationHelper
import com.google.inject.Inject
import cucumber.api.java.en._
import org.junit.Assert._

/**
 * @author jon
 */
class Interpolation @Inject()(helper: InterpolationHelper) {
  @Given( """a FlatFillInterpolator using (forward|backward) fill""")
  def flatFillInterpolator(direction: FlatFillInterpolator.Direction) {
    helper.interpolator = new FlatFillInterpolator[lang.Double](direction)
  }

  @Given( """a ZeroValueInterpolator""")
  def zeroValueInterpolator() {
    helper.interpolator = new ZeroValueInterpolator[lang.Double](0d)
  }

  @Given( """a LinearInterpolator""")
  def linearInterpolator() {
    helper.interpolator = new LinearInterpolator
  }

  @When( """interpolating for (\d+) given \((\d+), (\d+(?:\.\d+)?)?\) and \((\d+), (\d+(?:\.\d+)?)?\)""")
  def interpolate(x: Int, x1: Int, y1: lang.Double, x2: Int, y2: lang.Double) {
    helper.interpValue = helper.interpolator.getY(x, x1, y1, x2, y2)
  }

  @Then( """the interpolated value is (\d+(?:\.\d+)?)""")
  def checkInterp(y: lang.Double) {
    assertEquals(y, helper.interpValue, 1e-8)
  }

  @Then( """the interpolated value is null""")
  def checkInterpNull() {
    assertNull(helper.interpValue)
  }
}
