package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.relationship.Posting2AnimalRelationship
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultPosting2AnimalRelationshipDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Posting2AnimalRelationship1Id = "posting2AnimalRelationship id"
  val Posting2AnimalRelationship1Source = "posting2AnimalRelationship 1 source"
  val Posting2AnimalRelationship1Target = "posting2AnimalRelationship 1 target"
  val Posting2AnimalRelationship2Id = "posting2AnimalRelationship 2 id"
  val Posting2AnimalRelationship2Source = "posting2AnimalRelationship 2 source"
  val Posting2AnimalRelationship2Target = "posting2AnimalRelationship 2 target"
  val Posting2AnimalRelationship3Id = "posting2AnimalRelationship 3 id"
  
  val posting2AnimalRelationship1 = 
    new Posting2AnimalRelationship(Posting2AnimalRelationship1Id, Posting2AnimalRelationship1Source, Posting2AnimalRelationship1Target)
  val posting2AnimalRelationship2 = 
    new Posting2AnimalRelationship(Posting2AnimalRelationship2Id, Posting2AnimalRelationship2Source, Posting2AnimalRelationship2Target)
  val posting2AnimalRelationship3 = 
    new Posting2AnimalRelationship(Posting2AnimalRelationship3Id, Posting2AnimalRelationship1Source, Posting2AnimalRelationship2Target)
  lazy val defaultPosting2AnimalRelationshipDAO: DefaultPosting2AnimalRelationshipDAO = app.injector.instanceOf[DefaultPosting2AnimalRelationshipDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPosting2AnimalRelationshipDAO" should {
    
    "store a new Posting2AnimalRelationship in the DB when create is called" in {
      val f = defaultPosting2AnimalRelationshipDAO.create(posting2AnimalRelationship1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new Posting2AnimalRelationship " + t)
      }
    }
    
    "find the correct Posting2AnimalRelationship from the DB when find is called" in {
      delay
      val retrievedPosting2AnimalRelationship = defaultPosting2AnimalRelationshipDAO.findById(Posting2AnimalRelationship1Id)
      await(retrievedPosting2AnimalRelationship)
      retrievedPosting2AnimalRelationship onComplete {
        case Success(option) => option.get mustBe posting2AnimalRelationship1
        case Failure(t) => fail("failed to retrieve the Posting2AnimalRelationship " + t)
      }
    }
    
    "find the list of all Posting2AnimalRelationships when findAll is called" in {
      delay
      val saveFuture = defaultPosting2AnimalRelationshipDAO.create(posting2AnimalRelationship2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val saveFuture2 = defaultPosting2AnimalRelationshipDAO.create(posting2AnimalRelationship3)
          await(saveFuture2)
          saveFuture2 onComplete {
            case Success(n) => {
              n mustBe 1
              val listFuture = defaultPosting2AnimalRelationshipDAO.findAll()
              await(listFuture)
              listFuture onComplete {
                case Success(list) => {
                  list.size mustBe 3
                  list.contains(posting2AnimalRelationship1) mustBe true
                  list.contains(posting2AnimalRelationship2) mustBe true
                  list.contains(posting2AnimalRelationship3) mustBe true
                }
                case Failure(t) => fail("failed to retrieve list of Posting2AnimalRelationships " + t)
              }
            }
            case Failure(t) => fail("failed to save third Posting2AnimalRelationship in DB " + t)
          }
        }
        case Failure(t) => fail("failed to save second Posting2AnimalRelationship in DB " + t)
      } 
    }
    
    "find the correct relationships by source id" in {
      delay
      val findFuture = defaultPosting2AnimalRelationshipDAO.findBySourceId(Posting2AnimalRelationship1Source)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(posting2AnimalRelationship1) mustBe true
          list.contains(posting2AnimalRelationship2) mustBe false
          list.contains(posting2AnimalRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
      val findFuture2 = defaultPosting2AnimalRelationshipDAO.findBySourceId(Posting2AnimalRelationship2Source)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(posting2AnimalRelationship1) mustBe false
          list.contains(posting2AnimalRelationship2) mustBe true
          list.contains(posting2AnimalRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
    }
    
    "find the correct relationships by target id" in {
      delay
      val findFuture = defaultPosting2AnimalRelationshipDAO.findByTargetId(Posting2AnimalRelationship1Target)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(posting2AnimalRelationship1) mustBe true
          list.contains(posting2AnimalRelationship2) mustBe false
          list.contains(posting2AnimalRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
      val findFuture2 = defaultPosting2AnimalRelationshipDAO.findByTargetId(Posting2AnimalRelationship2Target)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(posting2AnimalRelationship1) mustBe false
          list.contains(posting2AnimalRelationship2) mustBe true
          list.contains(posting2AnimalRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
    }
    
    "delete a Posting2AnimalRelationship when deleteById is called" in {
      delay
      val deleteFuture = defaultPosting2AnimalRelationshipDAO.deleteById(Posting2AnimalRelationship1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingPosting2AnimalRelationshipListFuture = defaultPosting2AnimalRelationshipDAO.findAll()
          await(existingPosting2AnimalRelationshipListFuture)
          existingPosting2AnimalRelationshipListFuture onComplete {
            case Success(list) => {
              list.size mustBe 2
              list.contains(posting2AnimalRelationship1) mustBe false
              list.contains(posting2AnimalRelationship2) mustBe true
              val deleteFuture2 = defaultPosting2AnimalRelationshipDAO.deleteById(Posting2AnimalRelationship2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultPosting2AnimalRelationshipDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => {
                      list.size mustBe 1
                      val deleteFuture3 = defaultPosting2AnimalRelationshipDAO.deleteById(Posting2AnimalRelationship3Id)
                      await(deleteFuture3)
                      deleteFuture3 onComplete {
                        case Success(n) => {
                          n mustBe 1
                          val listFuture2 = defaultPosting2AnimalRelationshipDAO.findAll()
                          await(listFuture2)
                          listFuture2 onComplete {
                            case Success(list) => list.size mustBe 0
                            case Failure(t) => fail("failed to retrieve Posting2AnimalRelationship list for third time " + t)
                          }
                        }
                        case Failure(t) => fail("failed to delete third Posting2AnimalRelationship " + t)
                      }
                    }
                    case Failure(t) => fail("failed to retrieve Posting2AnimalRelationship list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second Posting2AnimalRelationship " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of Posting2AnimalRelationships when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first Posting2AnimalRelationship " + t)
      }
    }
    
  }

}