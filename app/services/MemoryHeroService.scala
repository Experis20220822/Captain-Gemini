/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.Hero

import scala.collection.mutable.ListBuffer
import scala.util.Try

class MemoryHeroService extends HeroService {

    val mutableList: ListBuffer[Hero] = ListBuffer.empty

    override def create(hero: Hero): Unit = mutableList += hero

/*    override def update(hero: Hero): Try[Hero] = {
      Try(mutableList.find(s => s.id == Hero.id).head)
        .map(s => {
          mutableList.filterInPlace(s => s.id != hero.id).addOne(hero);
          hero
        })
    }*/

    override def findById(id: Long): Option[Hero] = mutableList.find(s => id == s.id)

    override def findAll(): List[Hero] = mutableList.toList

  //  override def findByCountry(country: String): List[Hero] = mutableList.filter(s => country == s.country).toList


}
