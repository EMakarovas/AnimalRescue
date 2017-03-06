package com.emakarovas.animalrescue.persistence.writer.property

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.persistence.writer.ImageWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONBoolean
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class UserPropertyWriter @Inject() (
    implicit private val genderWriter: GenderWriter,
    implicit private val imageWriter: ImageWriter)
      extends PropertyWriter[UserModel] {
  
  override def write(any: Any): BSONValue = {
    any match {
      case str: String => BSONString(str)
      case g: Gender => genderWriter.write(g)
      case b: Boolean => BSONBoolean(b)
      case op: Option[Any] => op.get match {
        case str: String => BSONString(str)
        case img: ImageModel => imageWriter.write(img)
      }
    }
  }

}