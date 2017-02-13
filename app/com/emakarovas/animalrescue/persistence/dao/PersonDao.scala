package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.PersonModel
import scala.concurrent.Future

trait PersonDAO extends AbstractModelDAO[PersonModel] {
  def findByUserId(userId: String): Future[Option[PersonModel]]
}