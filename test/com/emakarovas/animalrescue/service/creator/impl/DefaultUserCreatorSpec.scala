package com.emakarovas.animalrescue.service.creator.impl

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.persistence.dao.PersonDAO
import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec
import org.scalatestplus.play.OneAppPerSuite
import org.scalatest.concurrent.ScalaFutures
import scala.util.Failure
import scala.util.Success
import com.emakarovas.animalrescue.service.creator.IsAvailableException
import com.emakarovas.animalrescue.testutil.TestModelGenerator
import com.emakarovas.animalrescue.service.creator.IncorrectUserIdException
import org.scalatest.mock.MockitoSugar

import org.mockito.Mockito._
import scala.concurrent.Future
import org.mockito.Mockito

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultUserCreatorSpec extends DelayedPlaySpec with OneAppPerSuite with MockitoSugar {

  lazy val defaultUserCreator: DefaultUserCreator = app.injector.instanceOf[DefaultUserCreator]
  lazy val userDAO: UserDAO = app.injector.instanceOf[UserDAO]
  lazy val personDAO: PersonDAO = app.injector.instanceOf[PersonDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
 
  "DefaultUserCreator" should {
    
    """"throw com.emakarovas.animalrescue.service.creator.IsAvailableException
    when create() is provided a UserModel with available=true""" in {
      val user = TestModelGenerator.getUser(1, available = true)
      val person = TestModelGenerator.getPerson(1)
      val f = defaultUserCreator.create(user, person)
      ScalaFutures.whenReady(f.failed) {
        e => e mustBe a [IsAvailableException]
      }
    }
     
    """"throw com.emakarovas.animalrescue.service.creator.IncorrectUserIdException
    when create() is provided a UserModel and a PersonModel
    where user.id!=person.userId""" in {
      val user = TestModelGenerator.getUser(1, available = false)
      val person = TestModelGenerator.getPerson(1, userId = user.id + "not equal")
      val f = defaultUserCreator.create(user, person)
      ScalaFutures.whenReady(f.failed) {
        e => e mustBe a [IncorrectUserIdException]
      }
    }
     
    "insert a UserModel and a PersonModel when create is called" in {
      val user = TestModelGenerator.getUser(1, available = false)
      val person = TestModelGenerator.getPerson(1, userId = user.id)
      val updatedUser = user.copy(available = true)
      val mockUserDAO = mock[UserDAO]
      val mockPersonDAO = mock[PersonDAO]
      val userCreator = new DefaultUserCreator(mockUserDAO, mockPersonDAO)
      when(mockUserDAO.create(user)).thenReturn(Future { 1 })
      when(mockPersonDAO.create(person)).thenReturn(Future { 1 })
      when(mockUserDAO.update(updatedUser)).thenReturn(Future { 1 })
      val f = userCreator.create(user, person)
      await(f)
      Mockito.verify(mockUserDAO, Mockito.times(1)).create(user)
      Mockito.verify(mockPersonDAO, Mockito.times(1)).create(person)
      Mockito.verify(mockUserDAO, Mockito.times(1)).update(updatedUser)
    }
    
  }
 
}