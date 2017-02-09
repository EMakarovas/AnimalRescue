package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.relationship.User2PersonRelationship
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultUser2PersonRelationshipDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val User2PersonRelationship1Id = "user2PersonRelationship id"
  val User2PersonRelationship1Source = "user2PersonRelationship 1 source"
  val User2PersonRelationship1Target = "user2PersonRelationship 1 target"
  val User2PersonRelationship2Id = "user2PersonRelationship 2 id"
  val User2PersonRelationship2Source = "user2PersonRelationship 2 source"
  val User2PersonRelationship2Target = "user2PersonRelationship 2 target"
  val User2PersonRelationship3Id = "user2PersonRelationship 3 id"
  
  val user2PersonRelationship1 = 
    new User2PersonRelationship(User2PersonRelationship1Id, User2PersonRelationship1Source, User2PersonRelationship1Target)
  val user2PersonRelationship2 = 
    new User2PersonRelationship(User2PersonRelationship2Id, User2PersonRelationship2Source, User2PersonRelationship2Target)
  val user2PersonRelationship3 = 
    new User2PersonRelationship(User2PersonRelationship3Id, User2PersonRelationship1Source, User2PersonRelationship2Target)
  lazy val defaultUser2PersonRelationshipDAO: DefaultUser2PersonRelationshipDAO = app.injector.instanceOf[DefaultUser2PersonRelationshipDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultUser2PersonRelationshipDAO" should {
    
    "store a new User2PersonRelationship in the DB when create is called" in {
      val f = defaultUser2PersonRelationshipDAO.create(user2PersonRelationship1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new User2PersonRelationship " + t)
      }
    }
    
    "find the correct User2PersonRelationship from the DB when find is called" in {
      delay
      val retrievedUser2PersonRelationship = defaultUser2PersonRelationshipDAO.findById(User2PersonRelationship1Id)
      await(retrievedUser2PersonRelationship)
      retrievedUser2PersonRelationship onComplete {
        case Success(option) => option.get mustBe user2PersonRelationship1
        case Failure(t) => fail("failed to retrieve the User2PersonRelationship " + t)
      }
    }
    
    "find the list of all User2PersonRelationships when findAll is called" in {
      delay
      val saveFuture = defaultUser2PersonRelationshipDAO.create(user2PersonRelationship2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val saveFuture2 = defaultUser2PersonRelationshipDAO.create(user2PersonRelationship3)
          await(saveFuture2)
          saveFuture2 onComplete {
            case Success(n) => {
              n mustBe 1
              val listFuture = defaultUser2PersonRelationshipDAO.findAll()
              await(listFuture)
              listFuture onComplete {
                case Success(list) => {
                  list.size mustBe 3
                  list.contains(user2PersonRelationship1) mustBe true
                  list.contains(user2PersonRelationship2) mustBe true
                  list.contains(user2PersonRelationship3) mustBe true
                }
                case Failure(t) => fail("failed to retrieve list of User2PersonRelationships " + t)
              }
            }
            case Failure(t) => fail("failed to save third User2PersonRelationship in DB " + t)
          }
        }
        case Failure(t) => fail("failed to save second User2PersonRelationship in DB " + t)
      } 
    }
    
    "find the correct relationships by source id" in {
      delay
      val findFuture = defaultUser2PersonRelationshipDAO.findBySourceId(User2PersonRelationship1Source)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(user2PersonRelationship1) mustBe true
          list.contains(user2PersonRelationship2) mustBe false
          list.contains(user2PersonRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
      val findFuture2 = defaultUser2PersonRelationshipDAO.findBySourceId(User2PersonRelationship2Source)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(user2PersonRelationship1) mustBe false
          list.contains(user2PersonRelationship2) mustBe true
          list.contains(user2PersonRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
    }
    
    "find the correct relationships by target id" in {
      delay
      val findFuture = defaultUser2PersonRelationshipDAO.findByTargetId(User2PersonRelationship1Target)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(user2PersonRelationship1) mustBe true
          list.contains(user2PersonRelationship2) mustBe false
          list.contains(user2PersonRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
      val findFuture2 = defaultUser2PersonRelationshipDAO.findByTargetId(User2PersonRelationship2Target)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(user2PersonRelationship1) mustBe false
          list.contains(user2PersonRelationship2) mustBe true
          list.contains(user2PersonRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
    }
    
    "delete a User2PersonRelationship when deleteById is called" in {
      delay
      val deleteFuture = defaultUser2PersonRelationshipDAO.deleteById(User2PersonRelationship1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingUser2PersonRelationshipListFuture = defaultUser2PersonRelationshipDAO.findAll()
          await(existingUser2PersonRelationshipListFuture)
          existingUser2PersonRelationshipListFuture onComplete {
            case Success(list) => {
              list.size mustBe 2
              list.contains(user2PersonRelationship1) mustBe false
              list.contains(user2PersonRelationship2) mustBe true
              val deleteFuture2 = defaultUser2PersonRelationshipDAO.deleteById(User2PersonRelationship2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultUser2PersonRelationshipDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => {
                      list.size mustBe 1
                      val deleteFuture3 = defaultUser2PersonRelationshipDAO.deleteById(User2PersonRelationship3Id)
                      await(deleteFuture3)
                      deleteFuture3 onComplete {
                        case Success(n) => {
                          n mustBe 1
                          val listFuture2 = defaultUser2PersonRelationshipDAO.findAll()
                          await(listFuture2)
                          listFuture2 onComplete {
                            case Success(list) => list.size mustBe 0
                            case Failure(t) => fail("failed to retrieve User2PersonRelationship list for third time " + t)
                          }
                        }
                        case Failure(t) => fail("failed to delete third User2PersonRelationship " + t)
                      }
                    }
                    case Failure(t) => fail("failed to retrieve User2PersonRelationship list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second User2PersonRelationship " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of User2PersonRelationships when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first User2PersonRelationship " + t)
      }
    }
    
  }

}