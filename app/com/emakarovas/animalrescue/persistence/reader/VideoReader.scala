package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.VideoConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class VideoReader extends AbstractEntityReader[VideoModel] {
  override def read(doc: BSONDocument): VideoModel = {
    val id = doc.getAs[String](MongoConstants.MongoId).get
    val url = doc.getAs[String](VideoConstants.Url).get
    VideoModel(id, url)
  }
}