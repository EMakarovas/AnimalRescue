package com.emakarovas.animalrescue.service.impl

import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.service.UserService

import javax.inject.Inject
import javax.inject.Singleton
import com.emakarovas.animalrescue.form.RegistrationForm
import scala.concurrent.Future
import com.emakarovas.animalrescue.model.UserModel

@Singleton
class DefaultUserService @Inject() (
    val userDAO: UserDAO) extends UserService {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  def handleRegistration(registrationForm: RegistrationForm): Future[Unit] = {
    userDAO.create(UserModel("id", "email", "psw", "salt", false)).transform((_) => Unit, (t) => t)
  }
  
}