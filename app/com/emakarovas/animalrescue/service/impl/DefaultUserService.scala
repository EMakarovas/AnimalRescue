package com.emakarovas.animalrescue.service.impl



import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.service.UserService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUserService @Inject() (
    val userDAO: UserDAO) extends UserService {
  
 
//  def handleRegistration(registrationForm: RegistrationForm): Future[Unit] = {
//    userDAO.create(UserModel("id", "email", "psw", "salt", false)).transform((_) => Unit, (t) => t)
//  }
  
}