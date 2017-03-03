package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.constants.CommentConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class CommentWriter extends AbstractEntityWriter[CommentModel] {
  override def write(comment: CommentModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> comment.id,
        CommentConstants.Date -> comment.date,
        CommentConstants.Text -> comment.text,
        CommentConstants.Name -> comment.name,
        CommentConstants.UserId -> comment.userId)
  }
}