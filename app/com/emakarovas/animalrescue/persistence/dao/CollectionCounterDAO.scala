package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.CollectionCounterEntity
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.model.enumeration.ModelType
import scala.concurrent.Future
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultCollectionCounterDAO
import com.emakarovas.animalrescue.model.AbstractModel

@ImplementedBy(classOf[DefaultCollectionCounterDAO])
trait CollectionCounterDAO extends AbstractDAO[CollectionCounterEntity] {
  def get(modelType: ModelType[AbstractModel]): Int
  def incrementAndGet(modelType: ModelType[AbstractModel]): Int
}