package com.emakarovas.animalrescue.persistence.writer.property

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.persistence.writer.ImageWriter
import com.emakarovas.animalrescue.persistence.writer.VideoWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.ColorWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.OfferTerminationReasonWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.SizeWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONBoolean
import reactivemongo.bson.BSONInteger
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class AnimalPropertyWriter @Inject() (
    implicit private val genderWriter: GenderWriter,
    implicit private val imageWriter: ImageWriter,
    implicit private val videoWriter: VideoWriter,
    implicit private val offerTerminationReasonWriter: OfferTerminationReasonWriter,
    implicit private val colorWriter: ColorWriter,
    implicit private val sizeWriter: SizeWriter)
      extends PropertyWriter[AnimalModel] {
  
  override def write(any: Any): BSONValue = {
    any match {
      case c: Color => colorWriter.write(c)
      case str: String => BSONString(str)
      case i: Int => BSONInteger(i)
      case s: Size => sizeWriter.write(s)
      case b: Boolean => BSONBoolean(b)
      case op: Option[Any] => op.get match {
        case g: Gender => genderWriter.write(g)
        case str: String => BSONString(str)
        case img: ImageModel => imageWriter.write(img)
        case vid: VideoModel => videoWriter.write(vid)
      }
    }
  }

}