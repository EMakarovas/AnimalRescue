package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.PersonModel
import scala.concurrent.Future
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultPersonDAO

@ImplementedBy(classOf[DefaultPersonDAO])
trait PersonDAO extends AbstractModelDAO[PersonModel] {
  def findByUserId(userId: String): Future[Option[PersonModel]]
}