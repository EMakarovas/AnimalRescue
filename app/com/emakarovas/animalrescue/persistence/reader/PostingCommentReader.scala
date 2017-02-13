package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.PostingCommentModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class PostingCommentReader extends AbstractModelReader[PostingCommentModel] {
  def read(doc: BSONDocument): PostingCommentModel = {
    val id = doc.getAs[String]("_id").get
    val date = doc.getAs[Date]("date").get
    val text = doc.getAs[String]("text").get
    val postingId = doc.getAs[String]("postingId").get
    PostingCommentModel(id, date, text, postingId)
  }
}