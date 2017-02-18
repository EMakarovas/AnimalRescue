package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.UserModel
import scala.concurrent.Future
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultUserDAO

@ImplementedBy(classOf[DefaultUserDAO])
trait UserDAO extends AbstractModelDAO[UserModel] with AbstractAvailableModelDAO[UserModel] {
  def findByEmail(email: String): Future[Option[UserModel]]
}