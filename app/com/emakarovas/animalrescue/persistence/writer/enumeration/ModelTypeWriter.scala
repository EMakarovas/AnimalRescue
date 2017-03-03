package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.enumeration.ModelType

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import com.emakarovas.animalrescue.model.AbstractModel

@Singleton
class ModelTypeWriter extends EnumerationWriter[ModelType[AbstractModel]] {
  override def write(modelType: ModelType[AbstractModel]) = BSONString(modelType.toString)
}