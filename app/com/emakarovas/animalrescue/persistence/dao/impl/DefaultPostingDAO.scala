package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.model.PostingModel
import com.emakarovas.animalrescue.persistence.dao.PostingDAO
import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.writer.PostingWriter
import com.emakarovas.animalrescue.persistence.reader.PostingReader

import javax.inject._
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.PostingModelCollectionType

@Singleton
class DefaultPostingDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: PostingWriter,
    implicit val reader: PostingReader) extends PostingDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  val collection = colFactory.getCollection(PostingModelCollectionType)
  
}