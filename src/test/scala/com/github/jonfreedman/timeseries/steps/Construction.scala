package com.github.jonfreedman.timeseries.steps

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.{lang, util}

import com.github.jonfreedman.timeseries.interpolator.ZeroValueInterpolator
import com.github.jonfreedman.timeseries.localdate.WeekdayLocalDateTraverser
import com.github.jonfreedman.timeseries.steps.helpers.ArrayTimeSeriesCollectionHelper
import com.github.jonfreedman.timeseries.steps.helpers.ArrayTimeSeriesCollectionHelper.LocalDateCollectionBuilder
import com.google.common.collect._
import com.google.inject.Inject
import cucumber.api.java.en._
import org.hamcrest.Matchers._
import org.junit.Assert._

import scala.beans.BeanProperty
import scala.collection.JavaConverters._

/**
 * @author jon
 */
class Construction @Inject()(helper: ArrayTimeSeriesCollectionHelper) {
  @Given( """a LocalDate ArrayTimeSeriesCollection with start date '(\d{4}-\d{2}-\d{2})' and end date '(\d{4}-\d{2}-\d{2})'""")
  def createLocalDateTimeSeriesCollection(s: String, e: String) {
    helper.builder = new LocalDateCollectionBuilder(LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(e, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
  }

  @Given("collection uses zero value interpolation")
  def useZeroValueInterpolation() {
    helper.builder = helper.builder.withInterpolator(new ZeroValueInterpolator(0d))
  }

  @Given("collection uses weekday traversal")
  def useWeekdayTraversal() {
    helper.builder = helper.builder.withTraverser(WeekdayLocalDateTraverser.factory())
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

  @Then( """keys contains '([a-z]+)'""")
  def containsKey(k: String) {
    assertThat(helper.collection.keySet(), hasItem(k))
  }

  @Then( """length is '(\d+)'""")
  def lengthIs(l: Int) {
    assertEquals(l, helper.collection.length())
  }

  @Then( """TimeSeries for key '([a-z]+)' contains \[(\d+(?:\.\d+)?(?:, \d+(?:\.\d+)?)*)\]""")
  def timeSeriesContainsValueOnly(key: String, expected: util.List[lang.Double]) {
    assertThat(Lists.newArrayList(helper.collection.get(key).ordinalIterator()), contains(expected.asScala.toSeq: _*))
  }

  @Then( """TimeSeries for key '([a-z]+)' contains values \[(\d+(?:\.\d+)?(?:, \d+(?:\.\d+)?)*)\] and timeValues \[('\d{4}-\d{2}-\d{2}'(?:, '\d{4}-\d{2}-\d{2}')*)\]""")
  def timeSeriesContainsValueWithTimeValue(key: String, expectedValues: util.List[lang.Double], expectedTimeValues: util.List[String]) {
    case class Entry(@BeanProperty key: LocalDate, @BeanProperty value: lang.Double) extends util.Map.Entry[LocalDate, lang.Double] {
      override def setValue(value: lang.Double): lang.Double = throw new UnsupportedOperationException

      override def toString: String = "%s -> %s".format(key, value)
    }
    val actual = Maps.newLinkedHashMap[LocalDate, lang.Double]
    helper.collection.get(key).timeValueIterator().asScala.foreach(e => actual.put(e.getKey, e.getValue))
    val expected = (expectedTimeValues.asScala.map(LocalDate.parse(_, DateTimeFormatter.ofPattern("''yyyy-MM-dd''"))) zip expectedValues.asScala).map { case (k, v) => Entry(k, v) }
    assertThat(actual.entrySet(), contains[util.Map.Entry[LocalDate, lang.Double]](expected.toSeq: _*))
    assertEquals("Extra items found", actual.size, expected.size)

  }
}
