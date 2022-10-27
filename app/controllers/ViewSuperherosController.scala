/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.i18n.I18nSupport
import play.api.mvc.MessagesControllerComponents
import services.AsyncHeroService
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.{ListSuperHeroes, ListSuperHeroesLayout, text_input}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext


@Singleton class ViewSuperherosController @Inject()(val mcc: MessagesControllerComponents, view: ListSuperHeroes, listView: ListSuperHeroesLayout, heroService: AsyncHeroService, textInputView: text_input)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {

  case class Data(val field: String) {}

  val form: Form[Data] = Form[Data](
    mapping("field" -> text)(Data.apply)(Data.unapply)
  )

  def index() = Action.async { implicit request =>
    heroService.findAll().map(xs => Ok(listView("View all heroes","Heroes Page","Select a hero", xs)))
  }

  def textInput() = Action { implicit req =>
    Ok(textInputView("", "", ""))
  }

}