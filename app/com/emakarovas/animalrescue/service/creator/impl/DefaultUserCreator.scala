package com.emakarovas.animalrescue.service.creator.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.persistence.dao.PersonDAO
import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.service.creator.UserCreator

import javax.inject.Inject
import javax.inject.Singleton
import com.emakarovas.animalrescue.service.creator.IsAvailableException
import com.emakarovas.animalrescue.persistence.dao.CreateException
import com.emakarovas.animalrescue.service.creator.IncorrectUserIdException

@Singleton
class DefaultUserCreator @Inject() (
    val userDAO: UserDAO,
    val personDAO: PersonDAO) extends UserCreator {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  override def create(user: UserModel, person: PersonModel): Future[Unit] = {
    if(user.available)
      return Future { throw new IsAvailableException }
    if(user.id!=person.userId)
      return Future { throw new IncorrectUserIdException }
    val userCreateFuture = userDAO.create(user)
    val personCreateFuture = userCreateFuture.flatMap((n) => personDAO.create(person))
    val availableUser = user.buildAvailable()
    val userUpdateFuture = personCreateFuture.flatMap((n) => userDAO.update(availableUser))
    userUpdateFuture.flatMap(_ => Future(Unit))
  }
  
}