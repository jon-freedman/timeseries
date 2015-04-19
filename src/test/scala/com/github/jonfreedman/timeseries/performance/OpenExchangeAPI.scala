package com.github.jonfreedman.timeseries.performance

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Collections
import javax.ws.rs._
import java.util
import java.lang
import javax.xml.bind.annotation.{XmlRootElement, XmlElement}

import com.github.jonfreedman.timeseries.performance.OpenExchangeAPI.{Response, DateParam}


/**
 * @author jon
 */
trait OpenExchangeAPI {
  @GET
  @Path("currencies.json")
  @Produces(Array("application/json"))
  def currencies: util.Map[String, String]

  @GET
  @Path("historical/{date}.json")
  @Produces(Array("application/json"))
  def historical(@PathParam("date") date: DateParam, @QueryParam("app_id") appId: String): Response
}

object OpenExchangeAPI {

  @XmlRootElement class Response() {
    @BeanParam
    @XmlElement var disclaimer: String = null
    @BeanParam
    @XmlElement val license: String = null
    @BeanParam
    @XmlElement val timestamp: Long = 0L
    @BeanParam
    @XmlElement(name = "base") val baseCurrency: String = null
    @BeanParam
    @XmlElement val rates: util.Map[String, lang.Double] = Collections.emptyMap()
  }

  case class DateParam(date: LocalDate) {
    override val toString: String = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
  }

  object DateParam {
    def valueOf(s: String): DateParam = DateParam(LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
  }

}