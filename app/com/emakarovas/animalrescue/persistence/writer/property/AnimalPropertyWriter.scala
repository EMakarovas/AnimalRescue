package com.emakarovas.animalrescue.persistence.writer.property

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason
import com.emakarovas.animalrescue.persistence.writer.ImageWriter
import com.emakarovas.animalrescue.persistence.writer.VideoWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.OfferTerminationReasonWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONBoolean
import reactivemongo.bson.BSONDouble
import reactivemongo.bson.BSONInteger
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class AnimalPropertyWriter @Inject() (
    implicit private val animalTypeWriter: AnimalTypeWriter,
    implicit private val genderWriter: GenderWriter,
    implicit private val imageWriter: ImageWriter,
    implicit private val videoWriter: VideoWriter,
    implicit private val offerTerminationReasonWriter: OfferTerminationReasonWriter)
      extends PropertyWriter[AnimalModel] {
  
  override def write(any: Any): BSONValue = {
    any match {
      case at: AnimalType => animalTypeWriter.write(at)
      case op: Option[Any] => op.get match {
        case str: String => BSONString(str)
        case i: Int => BSONInteger(i)
        case img: ImageModel => imageWriter.write(img)
        case vid: VideoModel => videoWriter.write(vid)
        case otr: OfferTerminationReason => offerTerminationReasonWriter.write(otr)
        case d: Double => BSONDouble(d)
      }
      case str: String => BSONString(str)
      case g: Gender => genderWriter.write(g)
      case b: Boolean => BSONBoolean(b)
    }
  }

}