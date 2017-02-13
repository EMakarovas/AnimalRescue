package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.PostingAnimalModel
import com.emakarovas.animalrescue.persistence.dao.PostingAnimalDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.PostingAnimalModelCollectionType
import com.emakarovas.animalrescue.persistence.reader.PostingAnimalReader
import com.emakarovas.animalrescue.persistence.writer.PostingAnimalWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultPostingAnimalDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: PostingAnimalWriter,
    implicit val reader: PostingAnimalReader) extends PostingAnimalDAO {
  
  val collection = colFactory.getCollection(PostingAnimalModelCollectionType)
 
  import scala.concurrent.ExecutionContext.Implicits.global
  
  override def findByPostingId(postingId: String): Future[List[PostingAnimalModel]] = {
    val selector = BSONDocument("postingId" -> postingId)
    collection.flatMap(_.find(selector)
        .cursor[PostingAnimalModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[PostingAnimalModel]]()))
  }
  
}