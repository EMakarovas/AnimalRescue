package com.emakarovas.animalrescue.persistence.writer.property

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.SearchAnimalModel
import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.persistence.writer.CommentWriter
import com.emakarovas.animalrescue.persistence.writer.SearchAnimalWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONBoolean
import reactivemongo.bson.BSONInteger
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class SearchPropertyWriter @Inject() (
    implicit private val commentWriter: CommentWriter,
    implicit private val searchAnimalWriter: SearchAnimalWriter,
    implicit private val animalTypeWriter: AnimalTypeWriter,
    implicit private val genderWriter: GenderWriter)
      extends PropertyWriter[SearchModel] {
  
  override def write(any: Any): BSONValue = {
    any match {
      case com: CommentModel => commentWriter.write(com)
      case sa: SearchAnimalModel => searchAnimalWriter.write(sa)
      case at: AnimalType => animalTypeWriter.write(at)
      case g: Gender => genderWriter.write(g)
      case b: Boolean => BSONBoolean(b)
      case op: Option[Any] => op.get match {
        case str: String => BSONString(str)
        case i: Int => BSONInteger(i)
      }
    }
  }

}