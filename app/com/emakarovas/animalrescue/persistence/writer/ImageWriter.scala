package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.constants.ImageConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class ImageWriter extends AbstractEntityWriter[ImageModel] {
  override def write(image: ImageModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> image.id,
        ImageConstants.Url -> image.url)
  }
}