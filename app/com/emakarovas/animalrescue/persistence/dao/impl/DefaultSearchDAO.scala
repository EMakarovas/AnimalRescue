package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.persistence.dao.SearchDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.Search
import com.emakarovas.animalrescue.persistence.reader.SearchReader
import com.emakarovas.animalrescue.persistence.writer.CommentWriter
import com.emakarovas.animalrescue.persistence.writer.SearchWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultSearchDAO @Inject() (
    colFactory: MongoCollectionFactory,
    val stringGenerator: StringGenerator,
    implicit val writer: SearchWriter,
    implicit val reader: SearchReader,
    implicit val commentWriter: CommentWriter) extends SearchDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  val collection = colFactory.getCollection(Search)
 
  override def findByUserId(userId: String): Future[List[SearchModel]] = {
    val selector = BSONDocument(SearchConstants.UserId -> userId)
    collection.flatMap(_.find(selector)
        .cursor[SearchModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[SearchModel]]()))
  }
  
  override def addToPotentialAnimalIdListBySearchAnimalId(searchAnimalId: String, potentialAnimalId: String): Future[Int] = {
    val selector = BSONDocument(SearchConstants.SearchAnimalList + "." + SearchAnimalConstants.Id -> searchAnimalId,
            "$elemMatch" -> BSONDocument(
                SearchConstants.SearchAnimalList + "." + SearchAnimalConstants.Id -> searchAnimalId),
            "$not" -> BSONDocument(
                "$elemMatch" -> BSONDocument(
                    SearchConstants.SearchAnimalList + "." + SearchAnimalConstants.PotentialAnimalIdList -> potentialAnimalId)))
    val update = BSONDocument("$push" -> BSONDocument(
        SearchConstants.SearchAnimalList + "." + SearchAnimalConstants.PotentialAnimalIdList -> potentialAnimalId))
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.n)
  }
  
}