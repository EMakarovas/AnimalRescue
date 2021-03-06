package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.constants.LocationConstants
import com.emakarovas.animalrescue.model.constants.PersonConstants
import com.emakarovas.animalrescue.model.constants.UserConstants
import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionType
import com.emakarovas.animalrescue.persistence.reader.UserReader
import com.emakarovas.animalrescue.persistence.writer.UserWriter
import com.emakarovas.animalrescue.persistence.writer.property.UserPropertyWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.indexes.IndexType
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultUserDAO @Inject() (
    val colFactory: MongoCollectionFactory,
    val stringGenerator: StringGenerator,
    implicit override val writer: UserWriter,
    implicit override val reader: UserReader,
    implicit override val propertyWriter: UserPropertyWriter) extends UserDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  val collection = colFactory.getCollection(MongoCollectionType.User)
  
  val indexList = List(
      buildIndex(UserConstants.Email, IndexType.Ascending, true),
      buildIndex(UserConstants.Person + "." + PersonConstants.Location + "." + LocationConstants.Country, IndexType.Ascending, false),
      buildIndex(UserConstants.Person + "." + PersonConstants.Location + "." + LocationConstants.City, IndexType.Ascending, false),
      buildIndex(UserConstants.Person + "." + PersonConstants.Location + "." + LocationConstants.Geolocation, IndexType.Geo2DSpherical, false)
  )
  setUpIndexes(indexList)
 
  override def findByEmail(email: String): Future[Option[UserModel]] = {
    val selector = BSONDocument(UserConstants.Email -> email)
    collection.flatMap(_.find(selector).one)
  }
  
}