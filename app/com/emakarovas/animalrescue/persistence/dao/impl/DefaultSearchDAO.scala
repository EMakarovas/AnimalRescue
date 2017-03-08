package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.persistence.dao.SearchDAO
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.Search
import com.emakarovas.animalrescue.persistence.reader.SearchReader
import com.emakarovas.animalrescue.persistence.writer.SearchWriter
import com.emakarovas.animalrescue.persistence.writer.property.SearchPropertyWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultSearchDAO @Inject() (
    val colFactory: MongoCollectionFactory,
    override val stringGenerator: StringGenerator,
    implicit override val writer: SearchWriter,
    implicit override val reader: SearchReader,
    implicit override val propertyWriter: SearchPropertyWriter) extends SearchDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  val collection = colFactory.getCollection(Search)
 
  override def findByUserId(userId: String): Future[List[SearchModel]] = {
    val selector = BSONDocument(SearchConstants.UserId -> userId)
    collection.flatMap(_.find(selector)
        .cursor[SearchModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[SearchModel]]()))
  }
  
  override def addToPotentialAnimalIdListBySearchAnimalId(id: String, searchAnimalId: String, potentialAnimalId: String): Future[Int] = {
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        SearchConstants.SearchAnimalList -> BSONDocument(
             "$elemMatch" -> BSONDocument(
                 MongoConstants.MongoId -> searchAnimalId)))
    val update = BSONDocument("$addToSet" -> BSONDocument(
        SearchConstants.SearchAnimalList + ".$." + SearchAnimalConstants.PotentialAnimalIdList -> potentialAnimalId))
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.nModified)
  }
  
}