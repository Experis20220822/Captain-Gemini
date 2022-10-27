/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

import play.api.libs.ws._
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class MarvelController @Inject() (ws: WSClient, val controllerComponents: ControllerComponents)(implicit val executionContext: ExecutionContext) extends BaseController {}