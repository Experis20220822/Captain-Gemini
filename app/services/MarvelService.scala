package services

import models.Hero

import scala.concurrent.Future

trait MarvelService {

  def findByCharacterStartsWith(startsWithName: String): Future[List[Hero]]
}
