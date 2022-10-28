/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.Hero

import scala.concurrent.Future

trait MarvelService {

  def findByCharacterStartsWith(startsWithName: String): Future[List[Hero]]
}
