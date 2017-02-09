package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.relationship.Animal2ImageRelationship
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultAnimal2ImageRelationshipDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Animal2ImageRelationship1Id = "animal2ImageRelationship id"
  val Animal2ImageRelationship1Source = "animal2ImageRelationship 1 source"
  val Animal2ImageRelationship1Target = "animal2ImageRelationship 1 target"
  val Animal2ImageRelationship2Id = "animal2ImageRelationship 2 id"
  val Animal2ImageRelationship2Source = "animal2ImageRelationship 2 source"
  val Animal2ImageRelationship2Target = "animal2ImageRelationship 2 target"
  val Animal2ImageRelationship3Id = "animal2ImageRelationship 3 id"
  
  val animal2ImageRelationship1 = 
    new Animal2ImageRelationship(Animal2ImageRelationship1Id, Animal2ImageRelationship1Source, Animal2ImageRelationship1Target)
  val animal2ImageRelationship2 = 
    new Animal2ImageRelationship(Animal2ImageRelationship2Id, Animal2ImageRelationship2Source, Animal2ImageRelationship2Target)
  val animal2ImageRelationship3 = 
    new Animal2ImageRelationship(Animal2ImageRelationship3Id, Animal2ImageRelationship1Source, Animal2ImageRelationship2Target)
  lazy val defaultAnimal2ImageRelationshipDAO: DefaultAnimal2ImageRelationshipDAO = app.injector.instanceOf[DefaultAnimal2ImageRelationshipDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultAnimal2ImageRelationshipDAO" should {
    
    "store a new Animal2ImageRelationship in the DB when create is called" in {
      val f = defaultAnimal2ImageRelationshipDAO.create(animal2ImageRelationship1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new Animal2ImageRelationship " + t)
      }
    }
    
    "find the correct Animal2ImageRelationship from the DB when find is called" in {
      delay
      val retrievedAnimal2ImageRelationship = defaultAnimal2ImageRelationshipDAO.findById(Animal2ImageRelationship1Id)
      await(retrievedAnimal2ImageRelationship)
      retrievedAnimal2ImageRelationship onComplete {
        case Success(option) => option.get mustBe animal2ImageRelationship1
        case Failure(t) => fail("failed to retrieve the Animal2ImageRelationship " + t)
      }
    }
    
    "find the list of all Animal2ImageRelationships when findAll is called" in {
      delay
      val saveFuture = defaultAnimal2ImageRelationshipDAO.create(animal2ImageRelationship2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val saveFuture2 = defaultAnimal2ImageRelationshipDAO.create(animal2ImageRelationship3)
          await(saveFuture2)
          saveFuture2 onComplete {
            case Success(n) => {
              n mustBe 1
              val listFuture = defaultAnimal2ImageRelationshipDAO.findAll()
              await(listFuture)
              listFuture onComplete {
                case Success(list) => {
                  list.size mustBe 3
                  list.contains(animal2ImageRelationship1) mustBe true
                  list.contains(animal2ImageRelationship2) mustBe true
                  list.contains(animal2ImageRelationship3) mustBe true
                }
                case Failure(t) => fail("failed to retrieve list of Animal2ImageRelationships " + t)
              }
            }
            case Failure(t) => fail("failed to save third Animal2ImageRelationship in DB " + t)
          }
        }
        case Failure(t) => fail("failed to save second Animal2ImageRelationship in DB " + t)
      } 
    }
    
    "find the correct relationships by source id" in {
      delay
      val findFuture = defaultAnimal2ImageRelationshipDAO.findBySourceId(Animal2ImageRelationship1Source)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(animal2ImageRelationship1) mustBe true
          list.contains(animal2ImageRelationship2) mustBe false
          list.contains(animal2ImageRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
      val findFuture2 = defaultAnimal2ImageRelationshipDAO.findBySourceId(Animal2ImageRelationship2Source)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(animal2ImageRelationship1) mustBe false
          list.contains(animal2ImageRelationship2) mustBe true
          list.contains(animal2ImageRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
    }
    
    "find the correct relationships by target id" in {
      delay
      val findFuture = defaultAnimal2ImageRelationshipDAO.findByTargetId(Animal2ImageRelationship1Target)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(animal2ImageRelationship1) mustBe true
          list.contains(animal2ImageRelationship2) mustBe false
          list.contains(animal2ImageRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
      val findFuture2 = defaultAnimal2ImageRelationshipDAO.findByTargetId(Animal2ImageRelationship2Target)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(animal2ImageRelationship1) mustBe false
          list.contains(animal2ImageRelationship2) mustBe true
          list.contains(animal2ImageRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
    }
    
    "delete a Animal2ImageRelationship when deleteById is called" in {
      delay
      val deleteFuture = defaultAnimal2ImageRelationshipDAO.deleteById(Animal2ImageRelationship1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingAnimal2ImageRelationshipListFuture = defaultAnimal2ImageRelationshipDAO.findAll()
          await(existingAnimal2ImageRelationshipListFuture)
          existingAnimal2ImageRelationshipListFuture onComplete {
            case Success(list) => {
              list.size mustBe 2
              list.contains(animal2ImageRelationship1) mustBe false
              list.contains(animal2ImageRelationship2) mustBe true
              val deleteFuture2 = defaultAnimal2ImageRelationshipDAO.deleteById(Animal2ImageRelationship2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultAnimal2ImageRelationshipDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => {
                      list.size mustBe 1
                      val deleteFuture3 = defaultAnimal2ImageRelationshipDAO.deleteById(Animal2ImageRelationship3Id)
                      await(deleteFuture3)
                      deleteFuture3 onComplete {
                        case Success(n) => {
                          n mustBe 1
                          val listFuture2 = defaultAnimal2ImageRelationshipDAO.findAll()
                          await(listFuture2)
                          listFuture2 onComplete {
                            case Success(list) => list.size mustBe 0
                            case Failure(t) => fail("failed to retrieve Animal2ImageRelationship list for third time " + t)
                          }
                        }
                        case Failure(t) => fail("failed to delete third Animal2ImageRelationship " + t)
                      }
                    }
                    case Failure(t) => fail("failed to retrieve Animal2ImageRelationship list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second Animal2ImageRelationship " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of Animal2ImageRelationships when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first Animal2ImageRelationship " + t)
      }
    }
    
  }

}