package com.github.jonfreedman.timeseries.steps

import java.lang

import com.github.jonfreedman.timeseries.calculation.{NonTemporalCalculator, SumCalculator}
import com.github.jonfreedman.timeseries.steps.helpers.{ArrayTimeSeriesCollectionHelper, CalculationHelper}
import com.google.inject.Inject
import cucumber.api.java.en._
import org.hamcrest.number.BigDecimalCloseTo
import org.junit.Assert._

import scala.collection.JavaConverters._

/**
 * @author jon
 */
class Calculations @Inject()(collectionHelper: ArrayTimeSeriesCollectionHelper, calculationHelper: CalculationHelper) {
  @When("sum is calculated")
  def sum() {
    val calculator = new SumCalculator[String]
    collectionHelper.collection.calculateNonTemporal(Seq[NonTemporalCalculator[String, _ >: lang.Double, _]](calculator).asJava)
    calculationHelper.bigDecimalResult = calculator.results
  }

  @Then( """BigDecimal result for '([a-z]+)' is (\d+(?:\.\d+)?)""")
  def doubleResult(key: String, result: java.math.BigDecimal) {
    assertThat(calculationHelper.bigDecimalResult.get(key), BigDecimalCloseTo.closeTo(result, java.math.BigDecimal.valueOf(1e-8d)))
  }
}