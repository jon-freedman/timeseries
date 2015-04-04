package com.github.jonfreedman.timeseries.steps

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.github.jonfreedman.timeseries.steps.helpers.ArrayTimeSeriesCollectionHelper
import com.github.jonfreedman.timeseries.steps.helpers.ArrayTimeSeriesCollectionHelper.LocalDateCollectionBuilder
import com.google.inject.Inject
import cucumber.api.java.en._
import org.junit.Assert._

/**
 * @author jon
 */
class Construction @Inject()(helper: ArrayTimeSeriesCollectionHelper) {
  @Given( """a LocalDate ArrayTimeSeriesCollection with start date '(\d{4}-\d{2}-\d{2})' and end date '(\d{4}-\d{2}-\d{2})'""")
  def createLocalDateTimeSeriesCollection(s: String, e: String) {
    helper.builder = new LocalDateCollectionBuilder(LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(e, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
  }

  @Given( """value for key '([a-z]+)' of \('(\d{4}-\d{2}-\d{2})' -> (\d+(?:\.\d+)?)\)""")
  def addValue(key: String, date: String, value: Double) {
    helper.builder = helper.builder.addValue(key, LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), value)
  }

  @Then( """the min date is '(\d{4}-\d{2}-\d{2})'""")
  def checkMinDate(d: String) {
    assertEquals(LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")), helper.collection.minValue())
  }

  @Then( """the max date is '(\d{4}-\d{2}-\d{2})'""")
  def checkMaxDate(d: String) {
    assertEquals(LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")), helper.collection.maxValue())
  }
}
