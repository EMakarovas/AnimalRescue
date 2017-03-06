package com.emakarovas.animalrescue.persistence.writer.property

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.persistence.writer.CommentWriter
import com.emakarovas.animalrescue.persistence.writer.ImageWriter
import com.emakarovas.animalrescue.persistence.writer.VideoWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class OfferPropertyWriter @Inject() (
    implicit private val commentWriter: CommentWriter,
    implicit private val imageWriter: ImageWriter,
    implicit private val videoWriter: VideoWriter)
      extends PropertyWriter[OfferModel] {
  
  override def write(any: Any): BSONValue = {
    any match {
      case com: CommentModel => commentWriter.write(com)
      case str: String => BSONString(str)
      case op: Option[Any] => op.get match {
        case img: ImageModel => imageWriter.write(img)
        case vid: VideoModel => videoWriter.write(vid)
      }
    }
  }

}