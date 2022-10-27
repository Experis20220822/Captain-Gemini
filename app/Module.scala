/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

import com.google.inject._
import org.mongodb.scala.{MongoClient, MongoDatabase}
import play.api.Configuration
import services.{AsyncHeroService, HeroService, MemoryHeroService, MongoHeroService}


class Module extends AbstractModule {
  override def configure(): Unit = {
    @Provides
    def databaseProvider(configuration: Configuration): MongoDatabase = {
      val username = configuration.get[String]("mongo.username")
      val password = configuration.get[String]("mongo.password")
      val database = configuration.get[String]("mongo.database")
      val url = configuration.get[String]("mongo.host")
      val port = configuration.get[String]("mongo.port")
      val fullURI = s"mongodb://$username:$password@$url:$port"
      val mongoClient: MongoClient = MongoClient(
        fullURI
      )
      mongoClient.getDatabase(database)
    }

    bind(classOf[AsyncHeroService]).to(classOf[MongoHeroService])
    bind(classOf[HeroService]).to(classOf[MemoryHeroService])
  }
}
