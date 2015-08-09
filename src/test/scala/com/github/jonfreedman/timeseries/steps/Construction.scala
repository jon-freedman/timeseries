package com.github.jonfreedman.timeseries.steps

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.function.{BiFunction, Predicate, Supplier}
import java.{lang, util}

import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection
import com.github.jonfreedman.timeseries.interpolator.FlatFillInterpolator.Direction
import com.github.jonfreedman.timeseries.interpolator.{FlatFillInterpolator, LinearInterpolator, ZeroValueInterpolator}
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
  @Given( """a LocalDate ArrayTimeSeriesCollection""")
  def createLocalDateTimeSeriesCollection() {
    helper.builder = new LocalDateCollectionBuilder
  }

  @Given("collection uses flat fill interpolation")
  def useFlatFillInterpolation() {
    helper.builder = helper.builder.withInterpolator(new FlatFillInterpolator[lang.Double](Direction.forward))
  }

  @Given("collection uses zero value interpolation")
  def useZeroValueInterpolation() {
    helper.builder = helper.builder.withInterpolator(new ZeroValueInterpolator(0d))
  }

  @Given("collection uses null value interpolation")
  def useNullValueInterpolation() {
    helper.builder = helper.builder.withInterpolator(new ZeroValueInterpolator(null))
  }

  @Given("collection uses linear interpolation")
  def useLinearInterpolation() {
    helper.builder = helper.builder.withInterpolator(new LinearInterpolator())
  }

  @Given("collection uses weekday traversal")
  def useWeekdayTraversal() {
    helper.builder = helper.builder.withTraverser(WeekdayLocalDateTraverser.factory)
  }

  @Given( """value for key '([a-z]+)' of \('(\d{4}-\d{2}-\d{2})' -> (-?\d+(?:\.\d+)?)\)""")
  def addValue(key: String, date: String, value: Double) {
    helper.builder = helper.builder.addValue(key, LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), value)
  }

  @When( """collection is filtered with keys = \[('[a-z]+'(?:, '[a-z]+')*)\]""")
  def subset(keys: util.List[String]) {
    val keySet = keys.asScala.map(s => s.replace("'", "")).toSet
    val tsc = helper.collection.get.filter(new Predicate[String] {
      override def test(t: String): Boolean = keySet.contains(t)
    })
    helper.collection = new Supplier[ArrayTimeSeriesCollection[String, LocalDate, lang.Double]] {
      override def get(): ArrayTimeSeriesCollection[String, LocalDate, lang.Double] = tsc
    }
  }

  @When( """collection is grouped with first key character function""")
  def groupByFirstKeyCharacter() {
    val tsc = helper.collection.get.group[String](new util.function.Function[String, String] {
      override def apply(t: String): String = t.substring(0, 1)
    }, new BiFunction[lang.Double, lang.Double, lang.Double] {
      override def apply(a: lang.Double, b: lang.Double): lang.Double = a + b
    })
    helper.collection = new Supplier[ArrayTimeSeriesCollection[String, LocalDate, lang.Double]] {
      override def get(): ArrayTimeSeriesCollection[String, LocalDate, lang.Double] = tsc
    }
  }

  @Then( """the min date is '(\d{4}-\d{2}-\d{2})'""")
  def checkMinDate(d: String) {
    assertEquals(LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")), helper.collection.get.minValue)
  }

  @Then( """the max date is '(\d{4}-\d{2}-\d{2})'""")
  def checkMaxDate(d: String) {
    assertEquals(LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")), helper.collection.get.maxValue)
  }

  @Then( """there are (\d+) keys""")
  def keyCountIs(l: Int) {
    assertEquals(l, helper.collection.get.keySet.size)
  }

  @Then( """keys contains '([a-z]+)'""")
  def containsKey(k: String) {
    assertThat(helper.collection.get.keySet, hasItem(k))
  }

  @Then( """length is (\d+)""")
  def lengthIs(l: Int) {
    assertEquals(l, helper.collection.get.length)
  }

  @Then( """TimeSeries for key '([a-z]+)' contains \[((?:(?:-?\d+(?:\.\d+)?)|null)(?:, (?:(?:-?\d+(?:\.\d+)?)|null))*)\]""")
  def timeSeriesContainsValueOnly(key: String, expected: util.List[String]) {
    val doubles = expected.asScala.map {
      case "null" => null
      case v => lang.Double.valueOf(v)
    }
    assertThat(Lists.newArrayList(helper.collection.get.get(key).ordinalIterator), contains(doubles.toSeq: _*))
  }

  @Then( """TimeSeries for key '([a-z]+)' contains values \[(-?\d+(?:\.\d+)?(?:, -?\d+(?:\.\d+)?)*)\] and timeValues \[('\d{4}-\d{2}-\d{2}'(?:, '\d{4}-\d{2}-\d{2}')*)\]""")
  def timeSeriesContainsValueWithTimeValue(key: String, expectedValues: util.List[lang.Double], expectedTimeValues: util.List[String]) {
    case class Entry(@BeanProperty key: LocalDate, @BeanProperty value: lang.Double) extends util.Map.Entry[LocalDate, lang.Double] {
      override def setValue(value: lang.Double): lang.Double = throw new UnsupportedOperationException

      override def toString: String = "%s -> %s".format(key, value)
    }
    val actual = Maps.newLinkedHashMap[LocalDate, lang.Double]
    helper.collection.get.get(key).timeValueIterator.asScala.foreach(e => actual.put(e.getKey, e.getValue))
    val expected = (expectedTimeValues.asScala.map(LocalDate.parse(_, DateTimeFormatter.ofPattern("''yyyy-MM-dd''"))) zip expectedValues.asScala).map { case (k, v) => Entry(k, v) }
    assertThat(actual.entrySet, contains[util.Map.Entry[LocalDate, lang.Double]](expected.toSeq: _*))
    assertEquals("Extra items found", actual.size, expected.size)
  }
}
