package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.PostingModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import java.util.Date

@Singleton
class PostingReader extends AbstractModelReader[PostingModel] {
  
  def read(doc: BSONDocument): PostingModel = {
    val id = doc.getAs[String]("_id").get
    val date = doc.getAs[Date]("date").get
    val text = doc.getAs[String]("text").get
    PostingModel(id, date, text)
  }
}

