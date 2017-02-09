package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.relationship.Person2ImageRelationship
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultPerson2ImageRelationshipDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Person2ImageRelationship1Id = "person2ImageRelationship id"
  val Person2ImageRelationship1Source = "person2ImageRelationship 1 source"
  val Person2ImageRelationship1Target = "person2ImageRelationship 1 target"
  val Person2ImageRelationship2Id = "person2ImageRelationship 2 id"
  val Person2ImageRelationship2Source = "person2ImageRelationship 2 source"
  val Person2ImageRelationship2Target = "person2ImageRelationship 2 target"
  val Person2ImageRelationship3Id = "person2ImageRelationship 3 id"
  
  val person2ImageRelationship1 = 
    new Person2ImageRelationship(Person2ImageRelationship1Id, Person2ImageRelationship1Source, Person2ImageRelationship1Target)
  val person2ImageRelationship2 = 
    new Person2ImageRelationship(Person2ImageRelationship2Id, Person2ImageRelationship2Source, Person2ImageRelationship2Target)
  val person2ImageRelationship3 = 
    new Person2ImageRelationship(Person2ImageRelationship3Id, Person2ImageRelationship1Source, Person2ImageRelationship2Target)
  lazy val defaultPerson2ImageRelationshipDAO: DefaultPerson2ImageRelationshipDAO = app.injector.instanceOf[DefaultPerson2ImageRelationshipDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPerson2ImageRelationshipDAO" should {
    
    "store a new Person2ImageRelationship in the DB when create is called" in {
      val f = defaultPerson2ImageRelationshipDAO.create(person2ImageRelationship1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new Person2ImageRelationship " + t)
      }
    }
    
    "find the correct Person2ImageRelationship from the DB when find is called" in {
      delay
      val retrievedPerson2ImageRelationship = defaultPerson2ImageRelationshipDAO.findById(Person2ImageRelationship1Id)
      await(retrievedPerson2ImageRelationship)
      retrievedPerson2ImageRelationship onComplete {
        case Success(option) => option.get mustBe person2ImageRelationship1
        case Failure(t) => fail("failed to retrieve the Person2ImageRelationship " + t)
      }
    }
    
    "find the list of all Person2ImageRelationships when findAll is called" in {
      delay
      val saveFuture = defaultPerson2ImageRelationshipDAO.create(person2ImageRelationship2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val saveFuture2 = defaultPerson2ImageRelationshipDAO.create(person2ImageRelationship3)
          await(saveFuture2)
          saveFuture2 onComplete {
            case Success(n) => {
              n mustBe 1
              val listFuture = defaultPerson2ImageRelationshipDAO.findAll()
              await(listFuture)
              listFuture onComplete {
                case Success(list) => {
                  list.size mustBe 3
                  list.contains(person2ImageRelationship1) mustBe true
                  list.contains(person2ImageRelationship2) mustBe true
                  list.contains(person2ImageRelationship3) mustBe true
                }
                case Failure(t) => fail("failed to retrieve list of Person2ImageRelationships " + t)
              }
            }
            case Failure(t) => fail("failed to save third Person2ImageRelationship in DB " + t)
          }
        }
        case Failure(t) => fail("failed to save second Person2ImageRelationship in DB " + t)
      } 
    }
    
    "find the correct relationships by source id" in {
      delay
      val findFuture = defaultPerson2ImageRelationshipDAO.findBySourceId(Person2ImageRelationship1Source)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(person2ImageRelationship1) mustBe true
          list.contains(person2ImageRelationship2) mustBe false
          list.contains(person2ImageRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
      val findFuture2 = defaultPerson2ImageRelationshipDAO.findBySourceId(Person2ImageRelationship2Source)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(person2ImageRelationship1) mustBe false
          list.contains(person2ImageRelationship2) mustBe true
          list.contains(person2ImageRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
    }
    
    "find the correct relationships by target id" in {
      delay
      val findFuture = defaultPerson2ImageRelationshipDAO.findByTargetId(Person2ImageRelationship1Target)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(person2ImageRelationship1) mustBe true
          list.contains(person2ImageRelationship2) mustBe false
          list.contains(person2ImageRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
      val findFuture2 = defaultPerson2ImageRelationshipDAO.findByTargetId(Person2ImageRelationship2Target)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(person2ImageRelationship1) mustBe false
          list.contains(person2ImageRelationship2) mustBe true
          list.contains(person2ImageRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
    }
    
    "delete a Person2ImageRelationship when deleteById is called" in {
      delay
      val deleteFuture = defaultPerson2ImageRelationshipDAO.deleteById(Person2ImageRelationship1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingPerson2ImageRelationshipListFuture = defaultPerson2ImageRelationshipDAO.findAll()
          await(existingPerson2ImageRelationshipListFuture)
          existingPerson2ImageRelationshipListFuture onComplete {
            case Success(list) => {
              list.size mustBe 2
              list.contains(person2ImageRelationship1) mustBe false
              list.contains(person2ImageRelationship2) mustBe true
              val deleteFuture2 = defaultPerson2ImageRelationshipDAO.deleteById(Person2ImageRelationship2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultPerson2ImageRelationshipDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => {
                      list.size mustBe 1
                      val deleteFuture3 = defaultPerson2ImageRelationshipDAO.deleteById(Person2ImageRelationship3Id)
                      await(deleteFuture3)
                      deleteFuture3 onComplete {
                        case Success(n) => {
                          n mustBe 1
                          val listFuture2 = defaultPerson2ImageRelationshipDAO.findAll()
                          await(listFuture2)
                          listFuture2 onComplete {
                            case Success(list) => list.size mustBe 0
                            case Failure(t) => fail("failed to retrieve Person2ImageRelationship list for third time " + t)
                          }
                        }
                        case Failure(t) => fail("failed to delete third Person2ImageRelationship " + t)
                      }
                    }
                    case Failure(t) => fail("failed to retrieve Person2ImageRelationship list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second Person2ImageRelationship " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of Person2ImageRelationships when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first Person2ImageRelationship " + t)
      }
    }
    
  }

}