package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.ModelType

case class CollectionCounterEntity
  (modelType: ModelType[AbstractModel],
   count: Int) extends AbstractModel(modelType.toString) with AbstractPersistableEntity