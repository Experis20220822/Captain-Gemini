/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

//import views.html.create
import views.html._

import services.AsyncHeroService

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global // This global ACTION gets view
import scala.util.hashing.MurmurHash3

import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.data.validation.Constraints.{max, min, nonEmpty}
import play.api.i18n.I18nSupport
//import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, MessagesControllerComponents}
import play.api.mvc._

import play.filters.csrf.CSRF
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.create
import views.html.text_input

import scala.concurrent.{ExecutionContext, Future}


case class HeroData(name: String, img: String, power: Int, intellect: Int, speed: Int, charisma: Int) // change to HeroData

class HeroController @Inject() (
                                val heroService: AsyncHeroService,
                                val mcc: MessagesControllerComponents, view: create, textInputView: text_input)
                               (implicit val executionContext: ExecutionContext) extends FrontendController(mcc)
  with I18nSupport {
  case class Data(val field: String) {}

  val form: Form[Data] = Form[Data](
    mapping("field" -> text)(Data.apply)(Data.unapply)
  )

  val heroForm: Form[HeroData] = Form[HeroData](
    mapping(
      "name" -> text.verifying(nonEmpty),
      "img" -> text.verifying(nonEmpty),
      "power" -> number.verifying(min(0), max(100)),
      "intellect" -> number.verifying(min(0), max(100)),
      "speed" -> number.verifying(min(0), max(100)),
      "charisma" -> number.verifying(min(0), max(100))
    )(HeroData.apply)
    (HeroData.unapply)
  )

  def index() = Action { implicit request =>
    Ok(view("Create Superhero", "Heading", "SomeText"))
  }

  def textInput() = Action { implicit req =>
    Ok(textInputView("", "", ""))
  }






  def init(): Action[AnyContent] = Action { implicit request =>
    Ok(view("Create Superhero", "Heading", "SomeText"))
  }

  def create() = Action {
    implicit request =>
      heroForm.bindFromRequest().fold(
        formWithErrors => {
          println("Nay!" + formWithErrors)
          BadRequest(view("Create Superhero", "Heading", "SomeText"))
        },
        heroData => {
          val id = MurmurHash3.stringHash(heroData.name)
          val newUser = models.Hero(
           // id,
            heroData.name,
            heroData.img,
            heroData.power,
            heroData.intellect,
            heroData.speed,
            heroData.charisma
          )
          println("Yay!" + newUser)
          heroService.create(newUser)
          Redirect(routes.ViewSuperherosController.index()) // this is what it should be doing -> Redirect(routes.StadiumController.show(id))
        }
      )
  }

/*  def show(id: Long): Action[AnyContent] = Action.async { implicit request =>
    val maybeHero = heroService.findById(id)
    maybeHero
      .map {
        case Some(stadium) => Ok(views.html.stadium.show(stadium))// needs to point to a show page which will be created soon
        case None => NotFound("Sorry, that HERO no existo")
      }
  }*/


}
