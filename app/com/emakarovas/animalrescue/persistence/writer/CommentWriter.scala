package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.CommentModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class CommentWriter extends AbstractModelWriter[CommentModel] {
  override def write(comment: CommentModel): BSONDocument = {
    BSONDocument(
        "_id" -> comment.id,
        "date" -> comment.date,
        "text" -> comment.text)
  }
}