package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.OfferModel
import scala.concurrent.Future
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultOfferDAO
import com.emakarovas.animalrescue.model.CommentModel

@ImplementedBy(classOf[DefaultOfferDAO])
trait OfferDAO extends AbstractUpdatableModelDAO[OfferModel] {
  def findByUserId(userId: String): Future[List[OfferModel]]
  def addToViewedByUserIdListById(offerId: String, userId: String): Future[Int]
}