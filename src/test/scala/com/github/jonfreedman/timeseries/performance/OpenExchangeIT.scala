package com.github.jonfreedman.timeseries.performance

import java.time.LocalDate

import com.github.jonfreedman.timeseries.performance.OpenExchangeAPI.DateParam
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.junit.{Ignore, Test, BeforeClass}
import org.junit.Assert._

/**
 * @author jon
 */
@Ignore("Write test, include caching of results as limited to 1000 requests per month")
class OpenExchangeIT {

}

object OpenExchangeIT {
  @BeforeClass def loadFXData() {
    Option(System.getProperty("openexchange_app_id")) match {
      case Some(appId) =>
        val client = new ResteasyClientBuilder().build()
        try {
          val api = client.target("http://openexchangerates.org/api/").proxy(classOf[OpenExchangeAPI])
          val data = api.historical(DateParam(LocalDate.of(2015, 4, 17)), appId)
        } finally {
          client.close()
        }
      case None => fail("Must set openexchange_app_id property to run these integration tests")
    }
  }
}
