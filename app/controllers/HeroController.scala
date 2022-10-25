package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.data.validation.Constraints.{max, min, nonEmpty}
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, MessagesControllerComponents}
import services.AsyncHeroService
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.{login, text_input}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.util.hashing.MurmurHash3


case class HeroData(name: String, img: String, power: Int, intellect: Int, speed: Int, charisma: Int) // change to HeroData

class HeroController @Inject() (
                                 val controllerComponents: ControllerComponents,
                                 val heroService: AsyncHeroService
                               ) extends BaseController
  with play.api.i18n.I18nSupport {
  def list() = Action.async { implicit request =>
    heroService.findAll().map(xs => Ok(views.html.stadium.stadiums(xs))
    )
  }


  val heroForm = Form(
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

  def init(): Action[AnyContent] = Action { implicit request =>
    Ok(views.html.stadium.create(stadiumForm))
  }

  def create() = Action {
    implicit request =>
      heroForm.bindFromRequest().fold(
        formWithErrors => {
          println("Nay!" + formWithErrors)
          BadRequest(views.html.stadium.create(formWithErrors))
        },
        heroData => {
        //  val id = MurmurHash3.stringHash(HeroData.name)
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
          Redirect(routes.StadiumController.show(id))
        }
      )
  }

  def show(id: Long): Action[AnyContent] = Action.async { implicit request =>
    val maybeStadium = heroService.findById(id)
    maybeStadium
      .map {
        case Some(stadium) => Ok(views.html.stadium.show(stadium))
        case None => NotFound("Sorry, that HERO no existo")
      }
  }
}
