package com.emakarovas.animalrescue.persistence.writer.property

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.OfferAnimalModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.persistence.writer.CommentWriter
import com.emakarovas.animalrescue.persistence.writer.ImageWriter
import com.emakarovas.animalrescue.persistence.writer.OfferAnimalWriter
import com.emakarovas.animalrescue.persistence.writer.VideoWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue
import reactivemongo.bson.BSONBoolean
import reactivemongo.bson.BSONInteger
import reactivemongo.bson.BSONDouble

@Singleton
class OfferPropertyWriter @Inject() (
    implicit private val commentWriter: CommentWriter,
    implicit private val imageWriter: ImageWriter,
    implicit private val videoWriter: VideoWriter,
    implicit private val offerAnimalWriter: OfferAnimalWriter,
    implicit private val genderWriter: GenderWriter)
      extends PropertyWriter[OfferModel] {
  
  override def write(any: Any): BSONValue = {
    any match {
      case com: CommentModel => commentWriter.write(com)
      case anim: OfferAnimalModel => offerAnimalWriter.write(anim)
      case g: Gender => genderWriter.write(g)
      case str: String => BSONString(str)
      case b: Boolean => BSONBoolean(b)
      case op: Option[Any] => op.get match {
        case s: String => BSONString(s)
        case i: Int => BSONInteger(i)
        case img: ImageModel => imageWriter.write(img)
        case vid: VideoModel => videoWriter.write(vid)
        case d: Double => BSONDouble(d)
      }
    }
  }

}