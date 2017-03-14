package com.emakarovas.animalrescue.service.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.mail.Mailer
import com.emakarovas.animalrescue.model.AccountSettingsModel
import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.enumeration.ModelType
import com.emakarovas.animalrescue.persistence.dao.CollectionCounterDAO
import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.persistence.dao.update.VersionedModelContainer
import com.emakarovas.animalrescue.service.PasswordService
import com.emakarovas.animalrescue.service.UserService
import com.emakarovas.animalrescue.service.exception.NotCreatedException
import com.emakarovas.animalrescue.service.exception.NotFoundException
import com.emakarovas.animalrescue.service.exception.NotUpdatedException
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import com.emakarovas.animalrescue.model.property.unique.UserUniqueProperty

@Singleton
class DefaultUserService @Inject() (
    val userDAO: UserDAO,
    val collectionCounterDAO: CollectionCounterDAO,
    val passwordService: PasswordService,
    val stringGenerator: StringGenerator,
    val mailer: Mailer) extends UserService {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  override def createUser(email: String, password: String): Future[Unit] = {
    val id = collectionCounterDAO.incrementAndGet(ModelType.User)
    val hashedPsw = passwordService.hashPassword(password)
    val activationString = stringGenerator.generate(30)
    val user = UserModel(id.toString, email, hashedPsw, None, Some(activationString), None, None, AccountSettingsModel(true))
    
    val createF = userDAO.create(user)
    createF.map(n => {
      if(n!=1) throw new NotCreatedException("User was not created")
      mailer.sendRegistrationEmail(user)
    })
  }

  
  override def findByEmail(email: String): Future[Option[UserModel]] = {
    userDAO.findByEmail(email)
  }
  
  override def activateUserById(id: String, activationString: String)(timesToTry: Int = 5): Future[Unit] = {
    if(timesToTry == -1)
      throw new NotUpdatedException(s"User with id $id not activated")
    val updatableUserF = userDAO.findUpdatableById(id)
    // remove activationString and send update request
    val updateF = updatableUserF.flatMap(op => {
      if(op.isEmpty) throw new NotFoundException(s"User with id $id not found")
      val version = op.get.version
      val user = op.get.model
      val newUser = user.copy(activationString = None)
      userDAO.update(VersionedModelContainer[UserModel](newUser, version))
    })
    updateF.flatMap(n => {
      if(n==0)
        activateUserById(id, activationString)(timesToTry-1)
      else Future {}
    })
  }
  
  override def startPasswordResetProcessByEmail(email: String)(timesToTry: Int = 5): Future[Unit] = {
    if(timesToTry == -1)
      throw new NotUpdatedException(s"Password reset process for user with email $email failed to start")
    val updatableUserF = userDAO.findUpdatableByUniqueProperty(UserUniqueProperty.EmailProperty(email))
    val updateF = updatableUserF.flatMap(op => {
      if(op.isEmpty) throw new NotFoundException(s"User with email $email not found")
      val version = op.get.version
      val user = op.get.model
      val passwordResetString = stringGenerator.generate(30)
      val newUser = user.copy(passwordResetString = Some(passwordResetString))
      userDAO.update(VersionedModelContainer[UserModel](newUser, version))
    })
    updateF.flatMap(n => {
      if(n==0)
        startPasswordResetProcessByEmail(email)(timesToTry-1)
      else Future {}
    })
  }
  
}