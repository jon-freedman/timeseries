package com.github.jonfreedman.timeseries.calculation

import java.time.LocalDate
import java.util.function.Supplier
import java.{lang, util}

import com.github.jonfreedman.timeseries.calculation.TemporalObservationMatcher._
import org.hamcrest.Matchers._
import org.junit.Assert._
import org.junit.Test

/**
 * @author jon
 */
class TestMaxValueCalculator {
  @Test def simpleMaxValidWithExplicitKeyAndResultMap() {
    val calc = new MaxValueCalculator[String, LocalDate, lang.Double]
    calc.observation("foo", supplierOf(LocalDate.of(2015, 4, 13)), -1d)
    calc.observation("foo", supplierOf(LocalDate.of(2015, 4, 14)), 2d)
    calc.observation("foo", supplierOf(LocalDate.of(2015, 4, 15)), 1d)

    assertThat(calc.result("foo"), observation(equalTo(LocalDate.of(2015, 4, 14)), closeTo(2d, 1e-8)))
    assertThat(calc.results(), hasEntry(equalTo("foo"), observation(equalTo(LocalDate.of(2015, 4, 14)), closeTo(2d, 1e-8))))
  }

  private def supplierOf[A](a: A): util.function.Supplier[A] = new Supplier[A] {
    override def get(): A = a
  }
}
