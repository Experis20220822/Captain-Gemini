/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, text}

import javax.inject.{Inject, Singleton}
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc.{MessagesControllerComponents, Request}
import play.filters.csrf.CSRF
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.login
import views.html.text_input

import scala.concurrent.{ExecutionContext, Future}


@Singleton class LoginController @Inject()(val mcc: MessagesControllerComponents, view: login, textInputView: text_input)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {

  case class Data(val field: String) {}

  val form: Form[Data] = Form[Data](
    mapping("field" -> text)(Data.apply)(Data.unapply)
  )

  def index() = Action { implicit request =>
    Ok(view("Title", "Heading", "SomeText"))
  }

  def textInput() = Action {implicit req =>
    Ok(textInputView("", "", ""))
  }
}