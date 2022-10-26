/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.Hero

import scala.concurrent.Future
import scala.util.Try

trait AsyncHeroService {
  def findById(id: Long): Future[Option[Hero]]

  def create(hero:Hero): Unit

  def update(hero: Hero): Future[Try[Hero]]

  def findAll(): Future[List[Hero]]

  def findByName(name: String): Future[Option[Hero]]

  def findByCountry(name: String): Future[List[Hero]]
}
