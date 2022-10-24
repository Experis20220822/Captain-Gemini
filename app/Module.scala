/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

import com.google.inject._
import config.AppConfig
import controllers.HomeController
import views.html.index

import java.lang.annotation.Target

class Module extends AbstractModule {
  override def configure(): Unit = {
    //bind(classOf[AppConfig]).to(classOf[HomeController])
  }
}
