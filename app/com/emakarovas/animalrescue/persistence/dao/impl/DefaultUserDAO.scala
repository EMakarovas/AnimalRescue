package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.writer.UserWriter
import com.emakarovas.animalrescue.persistence.reader.UserReader

import javax.inject._
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.UserModelCollectionType

@Singleton
class DefaultUserDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: UserWriter,
    implicit val reader: UserReader) extends UserDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  val collection = colFactory.getCollection(UserModelCollectionType)
  
}