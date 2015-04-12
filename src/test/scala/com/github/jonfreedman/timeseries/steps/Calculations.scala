package com.github.jonfreedman.timeseries.steps

import java.time.LocalDate
import java.time.chrono.ChronoLocalDate
import java.time.format.DateTimeFormatter
import java.{lang, math}

import com.github.jonfreedman.timeseries.calculation.TemporalObservationMatcher._
import com.github.jonfreedman.timeseries.calculation.number.SumCalculator
import com.github.jonfreedman.timeseries.calculation.{MaxValueCalculator, NonTemporalCalculator, TemporalCalculator}
import com.github.jonfreedman.timeseries.steps.helpers.{ArrayTimeSeriesCollectionHelper, CalculationHelper}
import com.google.inject.Inject
import cucumber.api.java.en._
import org.hamcrest.Matchers._
import org.junit.Assert._

import scala.collection.JavaConverters._

/**
 * @author jon
 */
class Calculations @Inject()(collectionHelper: ArrayTimeSeriesCollectionHelper, calculationHelper: CalculationHelper) {
  private val threshold = java.math.BigDecimal.valueOf(1e-8d)

  @When("sum is calculated")
  def sum() {
    val calculator = new SumCalculator[String]
    collectionHelper.collection.calculateNonTemporal(Seq[NonTemporalCalculator[String, _ >: lang.Double, _]](calculator).asJava)
    calculationHelper.bigDecimalResult = calculator.results
  }

  @When("max is calculated")
  def max() {
    val calculator = new MaxValueCalculator[String, LocalDate]
    collectionHelper.collection.calculateTemporal(Seq[TemporalCalculator[String, LocalDate, _ >: lang.Double, _]](calculator).asJava)
    calculationHelper.observationResult = calculator.results
  }

  @Then( """BigDecimal result for '([a-z]+)' is (\d+(?:\.\d+)?)""")
  def doubleResult(key: String, result: java.math.BigDecimal) {
    assertThat(calculationHelper.bigDecimalResult.get(key), closeTo(result, threshold))
  }

  @Then( """Observation result for '([a-z]+)' is \('(\d{4}-\d{2}-\d{2})' -> (\d+(?:\.\d+)?)\)""")
  def observationResult(key: String, timeValue: String, value: java.math.BigDecimal) {
    assertThat(calculationHelper.observationResult.get(key), observation[ChronoLocalDate, LocalDate, math.BigDecimal](equalTo(LocalDate.parse(timeValue, DateTimeFormatter.ofPattern("yyyy-MM-dd"))), closeTo(value, threshold)))
  }
}