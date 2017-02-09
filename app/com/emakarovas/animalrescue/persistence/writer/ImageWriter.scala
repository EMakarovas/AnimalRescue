package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.ImageModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class ImageWriter extends AbstractModelWriter[ImageModel] {
  override def write(image: ImageModel): BSONDocument = {
    BSONDocument(
        "_id" -> image.id,
        "url" -> image.url)
  }
}