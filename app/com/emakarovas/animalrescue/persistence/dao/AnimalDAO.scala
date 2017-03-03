package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AnimalModel
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultAnimalDAO

@ImplementedBy(classOf[DefaultAnimalDAO])
trait AnimalDAO extends AbstractUpdatableModelDAO[AnimalModel] {
  def findByOfferId(offerId: String): Future[List[AnimalModel]]
  def findByOwnerId(ownerId: String): Future[List[AnimalModel]]
}