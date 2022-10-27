/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.Hero
import org.mongodb.scala.connection.ClusterSettings
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{Document, MongoClient, MongoClientSettings, MongoCredential, MongoDatabase, ServerAddress}
import play.api.data.Forms.text
import play.api.data.validation.Constraints.nonEmpty

import javax.inject.Inject
import scala.concurrent.Future
import scala.util.Try

class MongoHeroService @Inject()(myCompanyDatabase: MongoDatabase) extends AsyncHeroService {

  val heroCollection = myCompanyDatabase.getCollection("hero")

  private def heroToDocument(hero: Hero) = {
    Document(
      "id" -> hero.id,
      "name" -> hero.name,
      "slogan" -> hero.slogan,
      "img" -> hero.img,
      "power" -> hero.power,
      "intellect" -> hero.intellect,
      "speed" -> hero.speed,
      "charisma" -> hero.charisma
    )
  }

  override def create(hero: Hero): Unit = {
    val document: Document = heroToDocument(hero)
    heroCollection
      .insertOne(document)
      .subscribe(
        r => println(s"Successful Insert $r"),
        t => t.printStackTrace(),
        () => "Insert Complete"
      )
  }

  private def documentToHero(d: Document) = {
    Hero(
      d.getLong("id"),
      d.getString("name"),
      d.getString("slogan"),
      d.getString("img"),
      d.getInteger("power"),
      d.getInteger("intellect"),
      d.getInteger("speed"),
      d.getInteger("charisma")
    )
  }

  override def findById(id: Long): Future[Option[Hero]] = {
    heroCollection
      .find(equal("_id", id))
      .map { d =>
        documentToHero(d)
      }
      .toSingle()
      .headOption()
  }

  override def findAll(): Future[List[Hero]] = heroCollection
    .find()
    .map(documentToHero)
    .foldLeft(List.empty[Hero])((list, hero) => hero :: list)
    .head()

  override def update(hero: Hero): Future[Try[Hero]] = ???

  override def findByName(name: String): Future[Option[Hero]] = ???

  override def findByCountry(name: String): Future[List[Hero]] = ???
}
