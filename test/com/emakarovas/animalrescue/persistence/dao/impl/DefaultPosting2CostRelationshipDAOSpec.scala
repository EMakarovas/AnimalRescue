package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.relationship.Posting2CostRelationship
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultPosting2CostRelationshipDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Posting2CostRelationship1Id = "posting2CostRelationship id"
  val Posting2CostRelationship1Source = "posting2CostRelationship 1 source"
  val Posting2CostRelationship1Target = "posting2CostRelationship 1 target"
  val Posting2CostRelationship2Id = "posting2CostRelationship 2 id"
  val Posting2CostRelationship2Source = "posting2CostRelationship 2 source"
  val Posting2CostRelationship2Target = "posting2CostRelationship 2 target"
  val Posting2CostRelationship3Id = "posting2CostRelationship 3 id"
  
  val posting2CostRelationship1 = 
    new Posting2CostRelationship(Posting2CostRelationship1Id, Posting2CostRelationship1Source, Posting2CostRelationship1Target)
  val posting2CostRelationship2 = 
    new Posting2CostRelationship(Posting2CostRelationship2Id, Posting2CostRelationship2Source, Posting2CostRelationship2Target)
  val posting2CostRelationship3 = 
    new Posting2CostRelationship(Posting2CostRelationship3Id, Posting2CostRelationship1Source, Posting2CostRelationship2Target)
  lazy val defaultPosting2CostRelationshipDAO: DefaultPosting2CostRelationshipDAO = app.injector.instanceOf[DefaultPosting2CostRelationshipDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPosting2CostRelationshipDAO" should {
    
    "store a new Posting2CostRelationship in the DB when create is called" in {
      val f = defaultPosting2CostRelationshipDAO.create(posting2CostRelationship1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new Posting2CostRelationship " + t)
      }
    }
    
    "find the correct Posting2CostRelationship from the DB when find is called" in {
      delay
      val retrievedPosting2CostRelationship = defaultPosting2CostRelationshipDAO.findById(Posting2CostRelationship1Id)
      await(retrievedPosting2CostRelationship)
      retrievedPosting2CostRelationship onComplete {
        case Success(option) => option.get mustBe posting2CostRelationship1
        case Failure(t) => fail("failed to retrieve the Posting2CostRelationship " + t)
      }
    }
    
    "find the list of all Posting2CostRelationships when findAll is called" in {
      delay
      val saveFuture = defaultPosting2CostRelationshipDAO.create(posting2CostRelationship2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val saveFuture2 = defaultPosting2CostRelationshipDAO.create(posting2CostRelationship3)
          await(saveFuture2)
          saveFuture2 onComplete {
            case Success(n) => {
              n mustBe 1
              val listFuture = defaultPosting2CostRelationshipDAO.findAll()
              await(listFuture)
              listFuture onComplete {
                case Success(list) => {
                  list.size mustBe 3
                  list.contains(posting2CostRelationship1) mustBe true
                  list.contains(posting2CostRelationship2) mustBe true
                  list.contains(posting2CostRelationship3) mustBe true
                }
                case Failure(t) => fail("failed to retrieve list of Posting2CostRelationships " + t)
              }
            }
            case Failure(t) => fail("failed to save third Posting2CostRelationship in DB " + t)
          }
        }
        case Failure(t) => fail("failed to save second Posting2CostRelationship in DB " + t)
      } 
    }
    
    "find the correct relationships by source id" in {
      delay
      val findFuture = defaultPosting2CostRelationshipDAO.findBySourceId(Posting2CostRelationship1Source)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(posting2CostRelationship1) mustBe true
          list.contains(posting2CostRelationship2) mustBe false
          list.contains(posting2CostRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
      val findFuture2 = defaultPosting2CostRelationshipDAO.findBySourceId(Posting2CostRelationship2Source)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(posting2CostRelationship1) mustBe false
          list.contains(posting2CostRelationship2) mustBe true
          list.contains(posting2CostRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
    }
    
    "find the correct relationships by target id" in {
      delay
      val findFuture = defaultPosting2CostRelationshipDAO.findByTargetId(Posting2CostRelationship1Target)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(posting2CostRelationship1) mustBe true
          list.contains(posting2CostRelationship2) mustBe false
          list.contains(posting2CostRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
      val findFuture2 = defaultPosting2CostRelationshipDAO.findByTargetId(Posting2CostRelationship2Target)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(posting2CostRelationship1) mustBe false
          list.contains(posting2CostRelationship2) mustBe true
          list.contains(posting2CostRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
    }
    
    "delete a Posting2CostRelationship when deleteById is called" in {
      delay
      val deleteFuture = defaultPosting2CostRelationshipDAO.deleteById(Posting2CostRelationship1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingPosting2CostRelationshipListFuture = defaultPosting2CostRelationshipDAO.findAll()
          await(existingPosting2CostRelationshipListFuture)
          existingPosting2CostRelationshipListFuture onComplete {
            case Success(list) => {
              list.size mustBe 2
              list.contains(posting2CostRelationship1) mustBe false
              list.contains(posting2CostRelationship2) mustBe true
              val deleteFuture2 = defaultPosting2CostRelationshipDAO.deleteById(Posting2CostRelationship2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultPosting2CostRelationshipDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => {
                      list.size mustBe 1
                      val deleteFuture3 = defaultPosting2CostRelationshipDAO.deleteById(Posting2CostRelationship3Id)
                      await(deleteFuture3)
                      deleteFuture3 onComplete {
                        case Success(n) => {
                          n mustBe 1
                          val listFuture2 = defaultPosting2CostRelationshipDAO.findAll()
                          await(listFuture2)
                          listFuture2 onComplete {
                            case Success(list) => list.size mustBe 0
                            case Failure(t) => fail("failed to retrieve Posting2CostRelationship list for third time " + t)
                          }
                        }
                        case Failure(t) => fail("failed to delete third Posting2CostRelationship " + t)
                      }
                    }
                    case Failure(t) => fail("failed to retrieve Posting2CostRelationship list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second Posting2CostRelationship " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of Posting2CostRelationships when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first Posting2CostRelationship " + t)
      }
    }
    
  }

}