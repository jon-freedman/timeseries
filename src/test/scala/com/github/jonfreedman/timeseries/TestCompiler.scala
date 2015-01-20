package com.github.jonfreedman.timeseries

import org.hamcrest.Matchers._
import org.junit.Assert._
import org.junit.Test

import scala.collection.Seq

/**
 * @author jon
 */
class TestCompiler {
  @Test def foo() = {
    assertThat(Seq(1,2,3).sum, is(6))
  }
}
