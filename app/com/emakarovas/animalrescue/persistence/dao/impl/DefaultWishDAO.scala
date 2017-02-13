package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.WishDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.WishModelCollectionType
import com.emakarovas.animalrescue.persistence.reader.WishReader
import com.emakarovas.animalrescue.persistence.writer.WishWriter

import javax.inject.Inject
import javax.inject.Singleton
import scala.concurrent.Future
import com.emakarovas.animalrescue.model.WishModel
import reactivemongo.bson.BSONDocument
import reactivemongo.api.Cursor

@Singleton
class DefaultWishDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: WishWriter,
    implicit val reader: WishReader) extends WishDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  val collection = colFactory.getCollection(WishModelCollectionType)
 
  override def findByUserId(userId: String): Future[List[WishModel]] = {
    val selector = BSONDocument("userId" -> userId)
    collection.flatMap(_.find(selector)
        .cursor[WishModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[WishModel]]()))
  }
  
}