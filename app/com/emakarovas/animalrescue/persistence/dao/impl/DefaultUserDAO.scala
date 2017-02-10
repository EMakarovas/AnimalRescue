package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.UserModelCollectionType
import com.emakarovas.animalrescue.persistence.reader.UserReader
import com.emakarovas.animalrescue.persistence.writer.UserWriter

import javax.inject.Inject
import javax.inject.Singleton
import scala.concurrent.Future
import com.emakarovas.animalrescue.model.UserModel
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultUserDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: UserWriter,
    implicit val reader: UserReader) extends UserDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  val collection = colFactory.getCollection(UserModelCollectionType)
 
  override def findByEmail(email: String): Future[Option[UserModel]] = {
    val selector = BSONDocument("email" -> email)
    collection.flatMap(_.find(selector).one)
  }
  
}