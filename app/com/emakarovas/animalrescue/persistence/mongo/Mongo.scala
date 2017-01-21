package com.emakarovas.animalrescue.persistence.mongo

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

import javax.inject._
import reactivemongo.api.DefaultDB
import reactivemongo.api.MongoConnection
import reactivemongo.api.MongoDriver
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.Macros
import reactivemongo.bson.document

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
