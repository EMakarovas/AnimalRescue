package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.constants.ImageConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class ImageReader extends AbstractEntityReader[ImageModel] {
  override def read(doc: BSONDocument): ImageModel = {
    val id = doc.getAs[String](MongoConstants.MongoId).get
    val url = doc.getAs[String](ImageConstants.Url).get
    ImageModel(id, url)
  }
}