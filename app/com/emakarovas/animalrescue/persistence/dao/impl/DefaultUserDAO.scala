package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.constants.UserConstants
import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.User
import com.emakarovas.animalrescue.persistence.reader.UserReader
import com.emakarovas.animalrescue.persistence.writer.UserWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultUserDAO @Inject() (
    colFactory: MongoCollectionFactory,
    val stringGenerator: StringGenerator,
    implicit val writer: UserWriter,
    implicit val reader: UserReader) extends UserDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  val collection = colFactory.getCollection(User)
 
  override def findByEmail(email: String): Future[Option[UserModel]] = {
    val selector = BSONDocument(UserConstants.Email -> email)
    collection.flatMap(_.find(selector).one)
  }
  
}