package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultUserDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val User1Id = "user id"
  val User1Email = "user 1 email"
  val User1HashedPassword = "user 1 hashed psw"
  val User1Salt = "user 1 salt"
  val User2Id = "user 2 id"
  val User2Email = "user 2 email"
  val User2HashedPassword = "user 2 hashed psw"
  val User2Salt = "user 2 salt"
  val User1EmailUpdated = "user 1 updated email"
  
  val user1 = new UserModel(User1Id, User1Email, User1HashedPassword, User1Salt)
  val user2 = new UserModel(User2Id, User2Email, User2HashedPassword, User2Salt)
  val updatedUser = new UserModel(User1Id, User1EmailUpdated, User1HashedPassword, User1Salt)
  lazy val defaultUserDAO: DefaultUserDAO = app.injector.instanceOf[DefaultUserDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultUserDAO" should {
    
    "store a new UserModel in the DB when create is called" in {
      val f = defaultUserDAO.create(user1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new UserModel " + t)
      }
    }
    
    "find the correct UserModel from the DB when find is called" in {
      delay
      val retrievedUser = defaultUserDAO.findById(User1Id)
      await(retrievedUser)
      retrievedUser onComplete {
        case Success(option) => option.get mustBe user1
        case Failure(t) => fail("failed to retrieve the UserModel " + t)
      }
    }
    
    "find the correct UserModel from the DB when findByEmail is called" in {
      delay
      val retrievedUser = defaultUserDAO.findByEmail(User1Email)
      await(retrievedUser)
      retrievedUser onComplete {
        case Success(option) => option.get mustBe user1
        case Failure(t) => fail("failed to retrieve the UserModel " + t)
      }
    }
    
    "find the list of all UserModels when findAll is called" in {
      delay
      val saveFuture = defaultUserDAO.create(user2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          val listFuture = defaultUserDAO.findAll()
          await(listFuture)
          listFuture onComplete {
            case Success(list) => list.contains(user1) mustBe true; list.contains(user2) mustBe true
            case Failure(t) => fail("failed to retrieve list of UserModels " + t)
          }
        }
        case Failure(t) => fail("failed to save second UserModel in DB " + t)
      } 
    }
    
    "update a UserModel when update is called" in {
      delay
      val updateFuture = defaultUserDAO.update(updatedUser)
      await(updateFuture)
      updateFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val updatedUserFuture = defaultUserDAO.findById(User1Id)
          await(updatedUserFuture)
          updatedUserFuture onComplete {
            case Success(userOption) => userOption.get.email mustBe User1EmailUpdated
            case Failure(t) => fail("failed to retrieve updated UserModel " + t)
          }
        }
        case Failure(t) => fail("failed to update UserModel " + t)
      }
    }
    
    "delete a UserModel when deleteById is called" in {
      delay
      val deleteFuture = defaultUserDAO.deleteById(User1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingUserListFuture = defaultUserDAO.findAll()
          await(existingUserListFuture)
          existingUserListFuture onComplete {
            case Success(list) => {
              list.contains(user1) mustBe false
              list.contains(user2) mustBe true
              val deleteFuture2 = defaultUserDAO.deleteById(User2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultUserDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => list.size mustBe 0
                    case Failure(t) => fail("failed to retrieve UserModel list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second UserModel " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of UserModels when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first UserModel " + t)
      }
    }
    
  }

}