package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.PostingModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONElement.converted

@Singleton
class PostingWriter extends AbstractModelWriter[PostingModel] {
  override def write(posting: PostingModel): BSONDocument = {
    BSONDocument(
        "_id" -> posting.id,
        "date" -> posting.date,
        "text" -> posting.text)
  }
}