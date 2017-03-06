package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.constants.UserConstants
import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.User
import com.emakarovas.animalrescue.persistence.reader.UserReader
import com.emakarovas.animalrescue.persistence.writer.UserWriter
import com.emakarovas.animalrescue.persistence.writer.property.UserPropertyWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultUserDAO @Inject() (
    val colFactory: MongoCollectionFactory,
    override val stringGenerator: StringGenerator,
    implicit override val writer: UserWriter,
    implicit override val reader: UserReader,
    implicit override val propertyWriter: UserPropertyWriter) extends UserDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  val collection = colFactory.getCollection(User)
 
  override def findByEmail(email: String): Future[Option[UserModel]] = {
    val selector = BSONDocument(UserConstants.Email -> email)
    collection.flatMap(_.find(selector).one)
  }
  
}