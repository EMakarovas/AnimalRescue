package com.emakarovas.animalrescue.persistence.mongo

import scala.concurrent.Future

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.DefaultDB
import reactivemongo.api.MongoConnection
import reactivemongo.api.MongoDriver

@Singleton
class Mongo @Inject() (configuration: play.api.Configuration) {
  
  private val Uri = configuration.underlying.getString("mongo.uri")
  private val DbName = configuration.underlying.getString("mongo.databaseName")

  import scala.concurrent.ExecutionContext.Implicits.global

  val driver = MongoDriver()
  val parsedUri = MongoConnection.parseURI(Uri)
  val connection = parsedUri.map(driver.connection(_))
  val futureConnection = Future.fromTry(connection)
  def db: Future[DefaultDB] = futureConnection.flatMap(_.database(DbName))

}
