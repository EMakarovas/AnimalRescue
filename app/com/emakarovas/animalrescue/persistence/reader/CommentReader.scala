package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.constants.CommentConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class CommentReader extends AbstractEntityReader[CommentModel] {
  def read(doc: BSONDocument): CommentModel = {
    val id = doc.getAs[String](MongoConstants.MongoId).get
    val date = doc.getAs[Date](CommentConstants.Date).get
    val text = doc.getAs[String](CommentConstants.Text).get
    val nameOpt = doc.getAs[String](CommentConstants.Name)
    val userIdOpt = doc.getAs[String](CommentConstants.UserId)
    CommentModel(id, date, text, nameOpt, userIdOpt)
  }
}