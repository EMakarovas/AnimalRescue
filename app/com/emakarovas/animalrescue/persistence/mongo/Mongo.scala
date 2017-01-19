package com.emakarovas.animalrescue.persistence.mongo

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

import com.emakarovas.animalrescue.model.PersonModel

import javax.inject._
import reactivemongo.api.DefaultDB
import reactivemongo.api.MongoConnection
import reactivemongo.api.MongoDriver
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.Macros
import reactivemongo.bson.document




/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Mongo @Inject() {

  val MongoUri = "mongodb://localhost:27017"
  val DatabaseName = "animalrescue";

  import scala.concurrent.ExecutionContext.Implicits.global // use any appropriate context

  // Connect to the database: Must be done only once per application
  val driver = MongoDriver()
  val parsedUri = MongoConnection.parseURI(MongoUri)
  val connection = parsedUri.map(driver.connection(_))

  // Database and collections: Get references
  val futureConnection = Future.fromTry(connection)
  def db: Future[DefaultDB] = futureConnection.flatMap(_.database(DatabaseName))
  def personCollection = db.map(_.collection("person"))

  // Write Documents: insert or update
  



  // TODO sort this
  implicit def personReader: BSONDocumentReader[PersonModel] = Macros.reader[PersonModel]
  // or provide a custom one

  def findPersonByAge(age: Int): Future[List[PersonModel]] =
    personCollection.flatMap(_.find(document("age" -> age)). // query builder
      cursor[PersonModel]().collect[List]()) // collect using the result cursor
      // ... deserializes the document using personReader
  
}
