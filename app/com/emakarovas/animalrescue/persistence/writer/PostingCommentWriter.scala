package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.PostingCommentModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class PostingCommentWriter extends AbstractModelWriter[PostingCommentModel] {
  override def write(postingComment: PostingCommentModel): BSONDocument = {
    BSONDocument(
        "_id" -> postingComment.id,
        "date" -> postingComment.date,
        "text" -> postingComment.text,
        "postingId" -> postingComment.postingId)
  }
}