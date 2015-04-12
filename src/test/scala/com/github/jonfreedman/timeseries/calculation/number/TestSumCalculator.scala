package com.github.jonfreedman.timeseries.calculation.number

import java.math

import org.hamcrest.Matchers._
import org.junit.Assert._
import org.junit.Test

/**
 * @author jon
 */
class TestSumCalculator {
  private val threshold = java.math.BigDecimal.valueOf(1e-8d)

  @Test def simpleSumValidWithExplicitKeyAndResultMap() {
    val calc = new SumCalculator[String]
    calc.observation("foo", 1)
    calc.observation("foo", 2)
    calc.observation("foo", 3)

    val expected = closeTo(math.BigDecimal.valueOf(6), threshold)
    assertThat(calc.result("foo"), expected)
    assertThat(calc.results(), hasEntry(equalTo("foo"), expected))
  }

  @Test def integerOverflowDoesNotOccur() {
    val calc = new SumCalculator[String]
    calc.observation("foo", 1)
    calc.observation("foo", Integer.MAX_VALUE)

    assertThat(calc.result("foo"), closeTo(math.BigDecimal.valueOf(Math.incrementExact(Integer.MAX_VALUE.asInstanceOf[Long])), threshold))
  }
}
