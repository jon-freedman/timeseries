package com.github.jonfreedman.timeseries.steps

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.github.jonfreedman.timeseries.localdate.{LocalDateTraverser, WeekdayLocalDateTraverser}
import com.github.jonfreedman.timeseries.steps.helpers.LocalDateTimeSeriesHelper
import com.google.inject.Inject
import cucumber.api.java.en._
import org.junit.Assert._

/**
 * @author jon
 */
class Iteration @Inject()(localDateHelper: LocalDateTimeSeriesHelper) {
  @Given( """a LocalDateTraverser with initial value '(\d{4}-\d{2}-\d{2})'""")
  def initialiseLocalDateTraverser(d: String) {
    localDateHelper.traverser = new LocalDateTraverser(LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
  }

  @Given( """a WeekdayLocalDateTraverser with initial value '(\d{4}-\d{2}-\d{2})'""")
  def initialiseWeekdayLocalDateTraverser(d: String) {
    localDateHelper.traverser = new WeekdayLocalDateTraverser(LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
  }

  @When("traversed to next")
  def traverseToNext() {
    localDateHelper.current = localDateHelper.traverser.next()
  }

  @When( """traversal skipped by (\d+)""")
  def skipN(n: Int) {
    localDateHelper.current = localDateHelper.traverser.skip(n)
  }

  @Then( """the traversed value is '(\d{4}-\d{2}-\d{2})'""")
  def validateTraversal(d: String) {
    assertEquals(LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")), localDateHelper.current)
  }
}