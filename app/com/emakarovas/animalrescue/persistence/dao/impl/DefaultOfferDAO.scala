package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.persistence.dao.OfferDAO
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.Offer
import com.emakarovas.animalrescue.persistence.reader.OfferReader
import com.emakarovas.animalrescue.persistence.writer.CommentWriter
import com.emakarovas.animalrescue.persistence.writer.OfferWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultOfferDAO @Inject() (
    colFactory: MongoCollectionFactory,
    val stringGenerator: StringGenerator,
    implicit val writer: OfferWriter,
    implicit val reader: OfferReader,
    implicit val commentWriter: CommentWriter) extends OfferDAO {
  
  val collection = colFactory.getCollection(Offer)
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  override def findByUserId(userId: String): Future[List[OfferModel]] = {
    val selector = BSONDocument(OfferConstants.UserId -> userId)
    collection.flatMap(_.find(selector)
        .cursor[OfferModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[OfferModel]]()))
  }
  
  override def addToViewedByUserIdListById(offerId: String, userId: String): Future[Int] = {
    val selector = BSONDocument(MongoConstants.MongoId -> offerId,
        "$not" -> BSONDocument(
            "$elemMatch" -> BSONDocument(
                OfferConstants.ViewedByUserIdList -> userId)))
    val update = BSONDocument("$push" -> BSONDocument(
        "viewedByUserIdList" -> userId))
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.n)
  }
  
}