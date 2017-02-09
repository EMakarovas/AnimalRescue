package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.relationship.User2PostingRelationship
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultUser2PostingRelationshipDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val User2PostingRelationship1Id = "user2PostingRelationship id"
  val User2PostingRelationship1Source = "user2PostingRelationship 1 source"
  val User2PostingRelationship1Target = "user2PostingRelationship 1 target"
  val User2PostingRelationship2Id = "user2PostingRelationship 2 id"
  val User2PostingRelationship2Source = "user2PostingRelationship 2 source"
  val User2PostingRelationship2Target = "user2PostingRelationship 2 target"
  val User2PostingRelationship3Id = "user2PostingRelationship 3 id"
  
  val user2PostingRelationship1 = 
    new User2PostingRelationship(User2PostingRelationship1Id, User2PostingRelationship1Source, User2PostingRelationship1Target)
  val user2PostingRelationship2 = 
    new User2PostingRelationship(User2PostingRelationship2Id, User2PostingRelationship2Source, User2PostingRelationship2Target)
  val user2PostingRelationship3 = 
    new User2PostingRelationship(User2PostingRelationship3Id, User2PostingRelationship1Source, User2PostingRelationship2Target)
  lazy val defaultUser2PostingRelationshipDAO: DefaultUser2PostingRelationshipDAO = app.injector.instanceOf[DefaultUser2PostingRelationshipDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultUser2PostingRelationshipDAO" should {
    
    "store a new User2PostingRelationship in the DB when create is called" in {
      val f = defaultUser2PostingRelationshipDAO.create(user2PostingRelationship1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new User2PostingRelationship " + t)
      }
    }
    
    "find the correct User2PostingRelationship from the DB when find is called" in {
      delay
      val retrievedUser2PostingRelationship = defaultUser2PostingRelationshipDAO.findById(User2PostingRelationship1Id)
      await(retrievedUser2PostingRelationship)
      retrievedUser2PostingRelationship onComplete {
        case Success(option) => option.get mustBe user2PostingRelationship1
        case Failure(t) => fail("failed to retrieve the User2PostingRelationship " + t)
      }
    }
    
    "find the list of all User2PostingRelationships when findAll is called" in {
      delay
      val saveFuture = defaultUser2PostingRelationshipDAO.create(user2PostingRelationship2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val saveFuture2 = defaultUser2PostingRelationshipDAO.create(user2PostingRelationship3)
          await(saveFuture2)
          saveFuture2 onComplete {
            case Success(n) => {
              n mustBe 1
              val listFuture = defaultUser2PostingRelationshipDAO.findAll()
              await(listFuture)
              listFuture onComplete {
                case Success(list) => {
                  list.size mustBe 3
                  list.contains(user2PostingRelationship1) mustBe true
                  list.contains(user2PostingRelationship2) mustBe true
                  list.contains(user2PostingRelationship3) mustBe true
                }
                case Failure(t) => fail("failed to retrieve list of User2PostingRelationships " + t)
              }
            }
            case Failure(t) => fail("failed to save third User2PostingRelationship in DB " + t)
          }
        }
        case Failure(t) => fail("failed to save second User2PostingRelationship in DB " + t)
      } 
    }
    
    "find the correct relationships by source id" in {
      delay
      val findFuture = defaultUser2PostingRelationshipDAO.findBySourceId(User2PostingRelationship1Source)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(user2PostingRelationship1) mustBe true
          list.contains(user2PostingRelationship2) mustBe false
          list.contains(user2PostingRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
      val findFuture2 = defaultUser2PostingRelationshipDAO.findBySourceId(User2PostingRelationship2Source)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(user2PostingRelationship1) mustBe false
          list.contains(user2PostingRelationship2) mustBe true
          list.contains(user2PostingRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
    }
    
    "find the correct relationships by target id" in {
      delay
      val findFuture = defaultUser2PostingRelationshipDAO.findByTargetId(User2PostingRelationship1Target)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(user2PostingRelationship1) mustBe true
          list.contains(user2PostingRelationship2) mustBe false
          list.contains(user2PostingRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
      val findFuture2 = defaultUser2PostingRelationshipDAO.findByTargetId(User2PostingRelationship2Target)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(user2PostingRelationship1) mustBe false
          list.contains(user2PostingRelationship2) mustBe true
          list.contains(user2PostingRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
    }
    
    "delete a User2PostingRelationship when deleteById is called" in {
      delay
      val deleteFuture = defaultUser2PostingRelationshipDAO.deleteById(User2PostingRelationship1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingUser2PostingRelationshipListFuture = defaultUser2PostingRelationshipDAO.findAll()
          await(existingUser2PostingRelationshipListFuture)
          existingUser2PostingRelationshipListFuture onComplete {
            case Success(list) => {
              list.size mustBe 2
              list.contains(user2PostingRelationship1) mustBe false
              list.contains(user2PostingRelationship2) mustBe true
              val deleteFuture2 = defaultUser2PostingRelationshipDAO.deleteById(User2PostingRelationship2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultUser2PostingRelationshipDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => {
                      list.size mustBe 1
                      val deleteFuture3 = defaultUser2PostingRelationshipDAO.deleteById(User2PostingRelationship3Id)
                      await(deleteFuture3)
                      deleteFuture3 onComplete {
                        case Success(n) => {
                          n mustBe 1
                          val listFuture2 = defaultUser2PostingRelationshipDAO.findAll()
                          await(listFuture2)
                          listFuture2 onComplete {
                            case Success(list) => list.size mustBe 0
                            case Failure(t) => fail("failed to retrieve User2PostingRelationship list for third time " + t)
                          }
                        }
                        case Failure(t) => fail("failed to delete third User2PostingRelationship " + t)
                      }
                    }
                    case Failure(t) => fail("failed to retrieve User2PostingRelationship list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second User2PostingRelationship " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of User2PostingRelationships when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first User2PostingRelationship " + t)
      }
    }
    
  }

}