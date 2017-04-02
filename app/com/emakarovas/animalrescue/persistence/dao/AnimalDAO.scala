package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultAnimalDAO

@ImplementedBy(classOf[DefaultAnimalDAO])
trait AnimalDAO extends AbstractUpdatableModelDAO[AnimalModel] {
  def findByOwnerId(ownerId: String): Future[List[AnimalModel]]
  def addSpecificTypeById(id: String, specificType: SpecificType[_ <: Animal]): Future[Int]
  def removeSpecificTypeById(id: String, specificType: SpecificType[_ <: Animal]): Future[Int]
}