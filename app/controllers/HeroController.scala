/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

//import views.html.create
import models.Mode
import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.data.validation.Constraints.{max, min, nonEmpty}
import play.api.i18n.I18nSupport
import services.AsyncHeroService

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.hashing.MurmurHash3
//import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, MessagesControllerComponents}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
//import play.api.mvc._

import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.ListSuperHeroesLayout
import views.html.createHero.heroes

//import scala.concurrent.{ExecutionContext, Future}



@Singleton class HeroController @Inject() (
                                            val mcc: MessagesControllerComponents,
                                            createView: heroes,
                                            listView: ListSuperHeroesLayout,
                                            heroService: AsyncHeroService)
                                extends FrontendController(mcc)
  with I18nSupport {

  case class HeroData(name: String, slogan: String, img: String, power: Int, intellect: Int, speed: Int, charisma: Int) // change to HeroData
  case class Data(val field: String) {}

  val form: Form[Data] = Form[Data](
    mapping("field" -> text)(Data.apply)(Data.unapply)
  )

  val heroForm: Form[HeroData] = Form[HeroData](
    mapping(
      "name" -> text.verifying(nonEmpty),
      "img" -> text.verifying(nonEmpty),
      "slogan" -> text.verifying(nonEmpty),
      "power" -> number.verifying(min(0), max(100)),
      "intellect" -> number.verifying(min(0), max(100)),
      "speed" -> number.verifying(min(0), max(100)),
      "charisma" -> number.verifying(min(0), max(100))
    )(HeroData.apply)
    (HeroData.unapply)
  )

  def list() = Action.async { implicit request =>
    heroService.findAll().map(xs => Ok(listView("View all heroes","Heroes Page","Select a hero", xs)))
  }

  def index(mode: Mode) = Action { implicit request =>
    Ok(createView(heroForm, mode))
  }

 // def textInput() = Action { implicit req =>
   // Ok(textInputView("", "", ""))
  //}


  def init(mode: Mode): Action[AnyContent] = Action { implicit request =>
    Ok(createView(heroForm, mode))
  }

  def create(mode: Mode) = Action { implicit request =>
      heroForm.bindFromRequest().fold(
        formWithErrors => {
          println("Nay!" + formWithErrors)
          BadRequest(createView(formWithErrors, mode))

        },
        heroData => {
          val id = MurmurHash3.stringHash(heroData.name)
          val newUser = models.Hero(
            id,
            heroData.name,
            heroData.img,
            heroData.slogan,
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

  def show(id: Long): Action[AnyContent] = Action.async { implicit request =>
    val maybeHero = heroService.findById(id)
    maybeHero
      .map {
        case Some(hero) => Ok(views.html.createHero.show(hero))// needs to point to a show page which will be created soon
        case None => NotFound("Sorry, that HERO no existo")
      }
  }


}
