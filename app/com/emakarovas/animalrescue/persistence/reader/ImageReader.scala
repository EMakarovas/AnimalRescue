package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.ImageModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class ImageReader extends AbstractModelReader[ImageModel] {
  def read(doc: BSONDocument): ImageModel = {
    val id = doc.getAs[String]("_id").get
    val url = doc.getAs[String]("url").get
    ImageModel(id, url)
  }
}