package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.enumeration.ModelType

import javax.inject.Singleton
import reactivemongo.bson.BSONString

@Singleton
class ModelTypeWriter extends EnumerationWriter[ModelType[_ <: AbstractModel]] {
  override def write(modelType: ModelType[_ <: AbstractModel]) = BSONString(modelType.toString)
}