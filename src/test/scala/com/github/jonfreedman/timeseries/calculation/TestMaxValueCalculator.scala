package com.github.jonfreedman.timeseries.calculation

import java.time.LocalDate
import java.time.chrono.ChronoLocalDate
import java.{math, util}
import java.util.function.Supplier

import com.github.jonfreedman.timeseries.calculation.TemporalObservationMatcher._
import org.hamcrest.Matchers._
import org.junit.Assert._
import org.junit.Test

/**
 * @author jon
 */
class TestMaxValueCalculator {
  private val threshold = java.math.BigDecimal.valueOf(1e-8d)

  @Test def simpleMaxValidWithExplicitKeyAndResultMap() {
    val calc = new MaxValueCalculator[String, LocalDate]
    calc.observation("foo", supplierOf(LocalDate.of(2015, 4, 13)), -1)
    calc.observation("foo", supplierOf(LocalDate.of(2015, 4, 14)), 2)
    calc.observation("foo", supplierOf(LocalDate.of(2015, 4, 15)), 1)

    val expectedValue = closeTo(math.BigDecimal.valueOf(2), threshold)

    assertThat(calc.result("foo"), observation[ChronoLocalDate, LocalDate, math.BigDecimal](equalTo(LocalDate.of(2015, 4, 14)), expectedValue))
    assertThat(calc.results(), hasEntry(equalTo("foo"), observation[ChronoLocalDate, LocalDate, math.BigDecimal](equalTo(LocalDate.of(2015, 4, 14)), expectedValue)))
  }

  private def supplierOf[A](a: A): util.function.Supplier[A] = new Supplier[A] {
    override def get(): A = a
  }
}
