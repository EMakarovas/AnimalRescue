package com.emakarovas.animalrescue.service

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.UserModel
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.service.impl.DefaultUserService

@ImplementedBy(classOf[DefaultUserService])
trait UserService {
  def createUser(email: String, password: String): Future[Unit]
  def findByEmail(email: String): Future[Option[UserModel]]
  def activateUserById(id: String, activationString: String)(timesToTry: Int): Future[Unit]
  def startPasswordResetProcessByEmail(email: String)(timesToTry: Int): Future[Unit]
}