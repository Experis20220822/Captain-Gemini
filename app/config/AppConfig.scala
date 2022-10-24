/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package config

import javax.inject.Inject
import play.api.Configuration

trait AppConfig {

  def contactHmrcAboutTaxUrl: String
  def externalReportProblemUrl: String
  def backUrlDestinationAllowList: Set[String]
  def loginCallback(continueUrl: String): String
  def enableLanguageSwitching: Boolean
  def useRefererFromRequest: Boolean

}

class Config @Inject() (configuration: Configuration) extends AppConfig {

  private def loadConfigString(key: String): String =
    configuration
      .getOptional[String](key)
      .getOrElse(configNotFoundError(key))

  private val contactHost = configuration
    .getOptional[String]("contact-frontend.host")
    .getOrElse("")

  override def contactHmrcAboutTaxUrl: String =
    loadConfigString("contactHmrcAboutTax.url")

  override lazy val externalReportProblemUrl =
    s"$contactHost/contact/problem_reports"

  override lazy val backUrlDestinationAllowList =
    loadConfigString("backUrlDestinationAllowList")
      .split(',')
      .filter(_.nonEmpty)
      .toSet

  override def loginCallback(continueUrl: String) = s"$contactHost$continueUrl"

  override def enableLanguageSwitching =
    configuration
      .getOptional[Boolean]("enableLanguageSwitching")
      .getOrElse(false)

  override def useRefererFromRequest: Boolean = configuration.getOptional[Boolean]("useRefererHeader").getOrElse(false)

  private def configNotFoundError(key: String) =
    throw new RuntimeException(s"Could not find config key '$key'")

  val cacheTtl: Int = configuration.get[Int]("mongodb.timeToLiveInSeconds")
}