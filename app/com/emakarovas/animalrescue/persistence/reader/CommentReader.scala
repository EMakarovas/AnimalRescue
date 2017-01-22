package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.CommentModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class CommentReader extends AbstractModelReader[CommentModel] {
  def read(doc: BSONDocument): CommentModel = {
    val id = doc.getAs[String]("_id").get
    val date = doc.getAs[Date]("date").get
    val text = doc.getAs[String]("text").get
    CommentModel(id, date, text)
  }
}