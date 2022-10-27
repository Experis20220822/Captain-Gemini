/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.Hero
import scala.util.Try

trait HeroService {

  def create(hero: Hero): Unit

//  def update(hero: Hero): Try[Hero]

  def findById(id: Long): Option[Hero]

  def findAll(): List[Hero]

//  def findByCountry(country: String): List[Hero]

}
