package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.enumeration.ModelType

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class ModelTypeReader extends EnumerationReader[ModelType[_ <: AbstractModel]] {
  override def read(bson: BSONValue): ModelType[_ <: AbstractModel] =
    bson match {
      case BSONString(str) => ModelType.valueOf(str)
      case _ => throw new IllegalBSONValueException()
    }
}