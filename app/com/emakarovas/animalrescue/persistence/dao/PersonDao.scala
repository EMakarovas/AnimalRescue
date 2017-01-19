package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.writer.PersonWriter

import javax.inject._
import com.emakarovas.animalrescue.model.PersonModel
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import com.emakarovas.animalrescue.persistence.exception.EmptyIdException
import reactivemongo.bson.BSONDocument

@Singleton
class PersonDao @Inject() (mongo: Mongo, implicit val personWriter: PersonWriter) {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  val collection = mongo.db.map(_.collection("person"))
  
  def create(person: PersonModel): Future[Unit] = {
    if(person.id==None)
      throw new EmptyIdException
    println(person)
    val future = collection.flatMap(_.insert(person)) // use personWriter
    future onComplete {
      case Failure(e) => e.printStackTrace()
      case Success(writeResult) => println("WO " + writeResult + ", person: " + person)
    }
    println("finished")
    future.map(_ => {})
  }
  
  def update(person: PersonModel): Future[Int] = {
    val selector = BSONDocument("_id" -> person.id )
    collection.flatMap(_.update(selector, person).map(_.n))
  }
  
}