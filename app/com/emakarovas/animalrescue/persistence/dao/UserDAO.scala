package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.UserModel
import scala.concurrent.Future

trait UserDAO extends AbstractModelDAO[UserModel] {
  def findByEmail(email: String): Future[Option[UserModel]]
}