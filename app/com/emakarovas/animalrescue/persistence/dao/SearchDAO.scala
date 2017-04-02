package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultSearchDAO

@ImplementedBy(classOf[DefaultSearchDAO])
trait SearchDAO extends AbstractUpdatableModelDAO[SearchModel] {
  def findByUserId(userId: String): Future[List[SearchModel]]
  /**
   * Adds an animal ID to the potentially matched animal ID list.
   * Does nothing if the potential ID already exists in the list
   * @param id The ID of the SearchModel
   * @param searchAnimalId The ID of the SearchAnimalModel that was matched
   * @param potentialAnimalId The ID of the AnimalModel that was matched
   */
  def addToPotentialAnimalIdListBySearchAnimalId(id: String, searchAnimalId: String, potentialAnimalId: String): Future[Int]
  def addSpecificTypeById(id: String, offerAnimalId: String, specificType: SpecificType[_ <: Animal]): Future[Int]
  def removeSpecificTypeById(id: String, offerAnimalId: String, specificType: SpecificType[_ <: Animal]): Future[Int]
}