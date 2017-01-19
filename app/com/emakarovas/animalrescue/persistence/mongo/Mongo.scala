package com.emakarovas.animalrescue.persistence.mongo

import scala.concurrent.{ ExecutionContext, Future }

import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{ DefaultDB, MongoConnection, MongoDriver }
import reactivemongo.bson.{
  BSONDocumentWriter, BSONDocumentReader, Macros, document
}

import javax.inject._
import play.api._
import java.util.concurrent.TimeUnit
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.util.Failure
import scala.util.Success
import com.emakarovas.animalrescue.model.PersonModel



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Mongo @Inject() {

  val mongoUri = "mongodb://localhost:27017/animalrescue"

  import ExecutionContext.Implicits.global // use any appropriate context

  // Connect to the database: Must be done only once per application
  val driver = MongoDriver()
  val parsedUri = MongoConnection.parseURI(mongoUri)
  val connection = parsedUri.map(driver.connection(_))

  // Database and collections: Get references
  val futureConnection = Future.fromTry(connection)
  def db: Future[DefaultDB] = futureConnection.flatMap(_.database("animalrescue"))
  def personCollection = db.map(_.collection("person"))

  // Write Documents: insert or update
  
  implicit def personWriter: BSONDocumentWriter[PersonModel] = Macros.writer[PersonModel]
  // or provide a custom one

  def createPerson(person: PersonModel): Future[Unit] = {
    println(person)
    val future = personCollection.flatMap(_.insert(person)) // use personWriter
    future onComplete {
      case Failure(e) => e.printStackTrace()
      case Success(writeResult) => println("WO " + writeResult + ", person: " + person)
    }
    println("finished")
    future.map(_ => {})
  }

  def updatePerson(person: PersonModel): Future[Int] = {
    val selector = document(
      "_id" -> person.id,
      "firstName" -> person.name,
      "lastName" -> person.surname
    )

    // Update the matching person
    personCollection.flatMap(_.update(selector, person).map(_.n))
  }

  implicit def personReader: BSONDocumentReader[PersonModel] = Macros.reader[PersonModel]
  // or provide a custom one

  def findPersonByAge(age: Int): Future[List[PersonModel]] =
    personCollection.flatMap(_.find(document("age" -> age)). // query builder
      cursor[PersonModel]().collect[List]()) // collect using the result cursor
      // ... deserializes the document using personReader
  
}
