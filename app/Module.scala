/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

import com.google.inject._
import config.AppConfig
import controllers.HomeController
import views.html.index

import java.lang.annotation.Target
import org.mongodb.scala.{MongoClient, MongoDatabase}
import play.api.Configuration
import services.{AsyncHeroService, MongoHeroService}


class Module extends AbstractModule {
  override def configure(): Unit = {
    @Provides
    def databaseProvider(configuration: Configuration): MongoDatabase = {
      val username = configuration.get[String]("mongo.username")
      val password = configuration.get[String]("mongo.password")
      val database = configuration.get[String]("mongo.database")
      val url = configuration.get[String]("mongo.host")
      val port = configuration.get[String]("mongo.port")
      val mongoClient: MongoClient = MongoClient(
        s"mongodb://$username:$password@$url:$port"
      )
      mongoClient.getDatabase(database)
    }

    bind(classOf[AsyncHeroService]).to(classOf[MongoHeroService])
  }
}
