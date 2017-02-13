package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.WishModel
import scala.concurrent.Future

trait WishDAO extends AbstractModelDAO[WishModel] {
  def findByUserId(userId: String): Future[List[WishModel]]
}