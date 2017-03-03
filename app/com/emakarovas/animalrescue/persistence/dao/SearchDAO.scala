package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.SearchModel
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultSearchDAO
import com.emakarovas.animalrescue.model.CommentModel

@ImplementedBy(classOf[DefaultSearchDAO])
trait SearchDAO extends AbstractUpdatableModelDAO[SearchModel] {
  def findByUserId(userId: String): Future[List[SearchModel]]
  def addToPotentialAnimalIdListBySearchAnimalId(searchAnimalId: String, potentialAnimalId: String): Future[Int]
}