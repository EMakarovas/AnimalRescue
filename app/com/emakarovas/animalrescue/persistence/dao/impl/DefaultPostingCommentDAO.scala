package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.PostingCommentDAO
import com.emakarovas.animalrescue.persistence.mongo.PostingCommentModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.reader.PostingCommentReader
import com.emakarovas.animalrescue.persistence.writer.PostingCommentWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.model.PostingCommentModel
import reactivemongo.api.Cursor
import scala.concurrent.Future

@Singleton
class DefaultPostingCommentDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: PostingCommentWriter,
    implicit val reader: PostingCommentReader) extends PostingCommentDAO {
  
  val collection = colFactory.getCollection(PostingCommentModelCollectionType)
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  override def findByPostingId(postingId: String): Future[List[PostingCommentModel]] = {
    val selector = BSONDocument("postingId" -> postingId)
    collection.flatMap(_.find(selector)
        .cursor[PostingCommentModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[PostingCommentModel]]()))
  }
  
}