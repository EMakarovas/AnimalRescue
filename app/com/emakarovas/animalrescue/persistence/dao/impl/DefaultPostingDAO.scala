package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.PostingDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.PostingModelCollectionType
import com.emakarovas.animalrescue.persistence.reader.PostingReader
import com.emakarovas.animalrescue.persistence.writer.PostingWriter

import javax.inject.Inject
import javax.inject.Singleton
import com.emakarovas.animalrescue.model.PostingModel
import reactivemongo.bson.BSONDocument
import reactivemongo.api.Cursor
import scala.concurrent.Future

@Singleton
class DefaultPostingDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: PostingWriter,
    implicit val reader: PostingReader) extends PostingDAO {
  
  val collection = colFactory.getCollection(PostingModelCollectionType)
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  override def findByUserId(userId: String): Future[List[PostingModel]] = {
    val selector = BSONDocument("userId" -> userId)
    collection.flatMap(_.find(selector)
        .cursor[PostingModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[PostingModel]]()))
  }
  
}