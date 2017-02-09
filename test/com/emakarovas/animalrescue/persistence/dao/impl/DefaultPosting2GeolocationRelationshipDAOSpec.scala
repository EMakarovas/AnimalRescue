package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.relationship.Posting2GeolocationRelationship
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultPosting2GeolocationRelationshipDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Posting2GeolocationRelationship1Id = "posting2GeolocationRelationship id"
  val Posting2GeolocationRelationship1Source = "posting2GeolocationRelationship 1 source"
  val Posting2GeolocationRelationship1Target = "posting2GeolocationRelationship 1 target"
  val Posting2GeolocationRelationship2Id = "posting2GeolocationRelationship 2 id"
  val Posting2GeolocationRelationship2Source = "posting2GeolocationRelationship 2 source"
  val Posting2GeolocationRelationship2Target = "posting2GeolocationRelationship 2 target"
  val Posting2GeolocationRelationship3Id = "posting2GeolocationRelationship 3 id"
  
  val posting2GeolocationRelationship1 = 
    new Posting2GeolocationRelationship(Posting2GeolocationRelationship1Id, Posting2GeolocationRelationship1Source, Posting2GeolocationRelationship1Target)
  val posting2GeolocationRelationship2 = 
    new Posting2GeolocationRelationship(Posting2GeolocationRelationship2Id, Posting2GeolocationRelationship2Source, Posting2GeolocationRelationship2Target)
  val posting2GeolocationRelationship3 = 
    new Posting2GeolocationRelationship(Posting2GeolocationRelationship3Id, Posting2GeolocationRelationship1Source, Posting2GeolocationRelationship2Target)
  lazy val defaultPosting2GeolocationRelationshipDAO: DefaultPosting2GeolocationRelationshipDAO = app.injector.instanceOf[DefaultPosting2GeolocationRelationshipDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPosting2GeolocationRelationshipDAO" should {
    
    "store a new Posting2GeolocationRelationship in the DB when create is called" in {
      val f = defaultPosting2GeolocationRelationshipDAO.create(posting2GeolocationRelationship1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new Posting2GeolocationRelationship " + t)
      }
    }
    
    "find the correct Posting2GeolocationRelationship from the DB when find is called" in {
      delay
      val retrievedPosting2GeolocationRelationship = defaultPosting2GeolocationRelationshipDAO.findById(Posting2GeolocationRelationship1Id)
      await(retrievedPosting2GeolocationRelationship)
      retrievedPosting2GeolocationRelationship onComplete {
        case Success(option) => option.get mustBe posting2GeolocationRelationship1
        case Failure(t) => fail("failed to retrieve the Posting2GeolocationRelationship " + t)
      }
    }
    
    "find the list of all Posting2GeolocationRelationships when findAll is called" in {
      delay
      val saveFuture = defaultPosting2GeolocationRelationshipDAO.create(posting2GeolocationRelationship2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val saveFuture2 = defaultPosting2GeolocationRelationshipDAO.create(posting2GeolocationRelationship3)
          await(saveFuture2)
          saveFuture2 onComplete {
            case Success(n) => {
              n mustBe 1
              val listFuture = defaultPosting2GeolocationRelationshipDAO.findAll()
              await(listFuture)
              listFuture onComplete {
                case Success(list) => {
                  list.size mustBe 3
                  list.contains(posting2GeolocationRelationship1) mustBe true
                  list.contains(posting2GeolocationRelationship2) mustBe true
                  list.contains(posting2GeolocationRelationship3) mustBe true
                }
                case Failure(t) => fail("failed to retrieve list of Posting2GeolocationRelationships " + t)
              }
            }
            case Failure(t) => fail("failed to save third Posting2GeolocationRelationship in DB " + t)
          }
        }
        case Failure(t) => fail("failed to save second Posting2GeolocationRelationship in DB " + t)
      } 
    }
    
    "find the correct relationships by source id" in {
      delay
      val findFuture = defaultPosting2GeolocationRelationshipDAO.findBySourceId(Posting2GeolocationRelationship1Source)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(posting2GeolocationRelationship1) mustBe true
          list.contains(posting2GeolocationRelationship2) mustBe false
          list.contains(posting2GeolocationRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
      val findFuture2 = defaultPosting2GeolocationRelationshipDAO.findBySourceId(Posting2GeolocationRelationship2Source)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(posting2GeolocationRelationship1) mustBe false
          list.contains(posting2GeolocationRelationship2) mustBe true
          list.contains(posting2GeolocationRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
    }
    
    "find the correct relationships by target id" in {
      delay
      val findFuture = defaultPosting2GeolocationRelationshipDAO.findByTargetId(Posting2GeolocationRelationship1Target)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(posting2GeolocationRelationship1) mustBe true
          list.contains(posting2GeolocationRelationship2) mustBe false
          list.contains(posting2GeolocationRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
      val findFuture2 = defaultPosting2GeolocationRelationshipDAO.findByTargetId(Posting2GeolocationRelationship2Target)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(posting2GeolocationRelationship1) mustBe false
          list.contains(posting2GeolocationRelationship2) mustBe true
          list.contains(posting2GeolocationRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
    }
    
    "delete a Posting2GeolocationRelationship when deleteById is called" in {
      delay
      val deleteFuture = defaultPosting2GeolocationRelationshipDAO.deleteById(Posting2GeolocationRelationship1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingPosting2GeolocationRelationshipListFuture = defaultPosting2GeolocationRelationshipDAO.findAll()
          await(existingPosting2GeolocationRelationshipListFuture)
          existingPosting2GeolocationRelationshipListFuture onComplete {
            case Success(list) => {
              list.size mustBe 2
              list.contains(posting2GeolocationRelationship1) mustBe false
              list.contains(posting2GeolocationRelationship2) mustBe true
              val deleteFuture2 = defaultPosting2GeolocationRelationshipDAO.deleteById(Posting2GeolocationRelationship2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultPosting2GeolocationRelationshipDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => {
                      list.size mustBe 1
                      val deleteFuture3 = defaultPosting2GeolocationRelationshipDAO.deleteById(Posting2GeolocationRelationship3Id)
                      await(deleteFuture3)
                      deleteFuture3 onComplete {
                        case Success(n) => {
                          n mustBe 1
                          val listFuture2 = defaultPosting2GeolocationRelationshipDAO.findAll()
                          await(listFuture2)
                          listFuture2 onComplete {
                            case Success(list) => list.size mustBe 0
                            case Failure(t) => fail("failed to retrieve Posting2GeolocationRelationship list for third time " + t)
                          }
                        }
                        case Failure(t) => fail("failed to delete third Posting2GeolocationRelationship " + t)
                      }
                    }
                    case Failure(t) => fail("failed to retrieve Posting2GeolocationRelationship list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second Posting2GeolocationRelationship " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of Posting2GeolocationRelationships when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first Posting2GeolocationRelationship " + t)
      }
    }
    
  }

}