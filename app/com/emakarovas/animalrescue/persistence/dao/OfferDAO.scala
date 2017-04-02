package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.OfferModel
import scala.concurrent.Future
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultOfferDAO
import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.persistence.dao.search.OfferSearch
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.SpecificType

@ImplementedBy(classOf[DefaultOfferDAO])
trait OfferDAO extends AbstractUpdatableModelDAO[OfferModel] {
  def findByUserId(userId: String): Future[List[OfferModel]]
  def addToViewedByUserIdListById(offerId: String, userId: String): Future[Int]
  def findByOfferSearch(offerSearch: OfferSearch[_ <: Animal], count: Int): Future[List[OfferModel]]
  def addSpecificTypeById(id: String, offerAnimalId: String, specificType: SpecificType[_ <: Animal]): Future[Int]
  def removeSpecificTypeById(id: String, offerAnimalId: String, specificType: SpecificType[_ <: Animal]): Future[Int]
}