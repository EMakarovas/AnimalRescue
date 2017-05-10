package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.emakarovas.animalrescue.persistence.dao.search.OfferFacet
import com.emakarovas.animalrescue.persistence.dao.search.OfferSearch
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultOfferDAO

@ImplementedBy(classOf[DefaultOfferDAO])
trait OfferDAO extends AbstractUpdatableModelDAO[OfferModel] {
  def findByUserId(userId: String): Future[List[OfferModel]]
  def addToViewedByUserIdSetById(offerId: String, userId: String): Future[Int]
  /**
   * Returns a Future[List[OfferModel]] containing ONLY the animals that satisfy the search criteria
   */
  def findByOfferSearch(offerSearch: OfferSearch[_ <: Animal], count: Int): Future[List[OfferModel]]
  def getFacetSearchData(offerSearch: OfferSearch[_ <: Animal]): Future[OfferFacet]
  def addSpecificTypeById(id: String, offerAnimalId: String, specificType: SpecificType[_ <: Animal]): Future[Int]
  def removeSpecificTypeById(id: String, offerAnimalId: String, specificType: SpecificType[_ <: Animal]): Future[Int]
}