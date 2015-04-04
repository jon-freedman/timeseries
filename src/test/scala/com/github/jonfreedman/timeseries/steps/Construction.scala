package com.github.jonfreedman.timeseries.steps

import cucumber.api.java.en.Given

/**
 * @author jon
 */
class Construction {
  @Given( """a LocalDate TimeSeriesCollection with start date '(\d{4}-\d{2}-\d{2})' and end date '(\d{4}-\d{2}-\d{2})'""")
  def createLocalDateTimeSeriesCollection(s: String, e: String) {

  }
}
