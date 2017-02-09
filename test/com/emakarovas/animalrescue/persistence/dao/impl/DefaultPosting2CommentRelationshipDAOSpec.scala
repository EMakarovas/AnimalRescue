package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.relationship.Posting2CommentRelationship
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultPosting2CommentRelationshipDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Posting2CommentRelationship1Id = "posting2CommentRelationship id"
  val Posting2CommentRelationship1Source = "posting2CommentRelationship 1 source"
  val Posting2CommentRelationship1Target = "posting2CommentRelationship 1 target"
  val Posting2CommentRelationship2Id = "posting2CommentRelationship 2 id"
  val Posting2CommentRelationship2Source = "posting2CommentRelationship 2 source"
  val Posting2CommentRelationship2Target = "posting2CommentRelationship 2 target"
  val Posting2CommentRelationship3Id = "posting2CommentRelationship 3 id"
  
  val posting2CommentRelationship1 = 
    new Posting2CommentRelationship(Posting2CommentRelationship1Id, Posting2CommentRelationship1Source, Posting2CommentRelationship1Target)
  val posting2CommentRelationship2 = 
    new Posting2CommentRelationship(Posting2CommentRelationship2Id, Posting2CommentRelationship2Source, Posting2CommentRelationship2Target)
  val posting2CommentRelationship3 = 
    new Posting2CommentRelationship(Posting2CommentRelationship3Id, Posting2CommentRelationship1Source, Posting2CommentRelationship2Target)
  lazy val defaultPosting2CommentRelationshipDAO: DefaultPosting2CommentRelationshipDAO = app.injector.instanceOf[DefaultPosting2CommentRelationshipDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPosting2CommentRelationshipDAO" should {
    
    "store a new Posting2CommentRelationship in the DB when create is called" in {
      val f = defaultPosting2CommentRelationshipDAO.create(posting2CommentRelationship1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new Posting2CommentRelationship " + t)
      }
    }
    
    "find the correct Posting2CommentRelationship from the DB when find is called" in {
      delay
      val retrievedPosting2CommentRelationship = defaultPosting2CommentRelationshipDAO.findById(Posting2CommentRelationship1Id)
      await(retrievedPosting2CommentRelationship)
      retrievedPosting2CommentRelationship onComplete {
        case Success(option) => option.get mustBe posting2CommentRelationship1
        case Failure(t) => fail("failed to retrieve the Posting2CommentRelationship " + t)
      }
    }
    
    "find the list of all Posting2CommentRelationships when findAll is called" in {
      delay
      val saveFuture = defaultPosting2CommentRelationshipDAO.create(posting2CommentRelationship2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val saveFuture2 = defaultPosting2CommentRelationshipDAO.create(posting2CommentRelationship3)
          await(saveFuture2)
          saveFuture2 onComplete {
            case Success(n) => {
              n mustBe 1
              val listFuture = defaultPosting2CommentRelationshipDAO.findAll()
              await(listFuture)
              listFuture onComplete {
                case Success(list) => {
                  list.size mustBe 3
                  list.contains(posting2CommentRelationship1) mustBe true
                  list.contains(posting2CommentRelationship2) mustBe true
                  list.contains(posting2CommentRelationship3) mustBe true
                }
                case Failure(t) => fail("failed to retrieve list of Posting2CommentRelationships " + t)
              }
            }
            case Failure(t) => fail("failed to save third Posting2CommentRelationship in DB " + t)
          }
        }
        case Failure(t) => fail("failed to save second Posting2CommentRelationship in DB " + t)
      } 
    }
    
    "find the correct relationships by source id" in {
      delay
      val findFuture = defaultPosting2CommentRelationshipDAO.findBySourceId(Posting2CommentRelationship1Source)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(posting2CommentRelationship1) mustBe true
          list.contains(posting2CommentRelationship2) mustBe false
          list.contains(posting2CommentRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
      val findFuture2 = defaultPosting2CommentRelationshipDAO.findBySourceId(Posting2CommentRelationship2Source)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(posting2CommentRelationship1) mustBe false
          list.contains(posting2CommentRelationship2) mustBe true
          list.contains(posting2CommentRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by source id " + t)
      }
    }
    
    "find the correct relationships by target id" in {
      delay
      val findFuture = defaultPosting2CommentRelationshipDAO.findByTargetId(Posting2CommentRelationship1Target)
      await(findFuture)
      findFuture onComplete {
        case Success(list) => {
          list.size mustBe 1
          list.contains(posting2CommentRelationship1) mustBe true
          list.contains(posting2CommentRelationship2) mustBe false
          list.contains(posting2CommentRelationship3) mustBe false
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
      val findFuture2 = defaultPosting2CommentRelationshipDAO.findByTargetId(Posting2CommentRelationship2Target)
      await(findFuture2)
      findFuture2 onComplete {
        case Success(list) => {
          list.size mustBe 2
          list.contains(posting2CommentRelationship1) mustBe false
          list.contains(posting2CommentRelationship2) mustBe true
          list.contains(posting2CommentRelationship3) mustBe true
        }
        case Failure(t) => fail("failed to retrieve relationships by target id " + t)
      }
    }
    
    "delete a Posting2CommentRelationship when deleteById is called" in {
      delay
      val deleteFuture = defaultPosting2CommentRelationshipDAO.deleteById(Posting2CommentRelationship1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingPosting2CommentRelationshipListFuture = defaultPosting2CommentRelationshipDAO.findAll()
          await(existingPosting2CommentRelationshipListFuture)
          existingPosting2CommentRelationshipListFuture onComplete {
            case Success(list) => {
              list.size mustBe 2
              list.contains(posting2CommentRelationship1) mustBe false
              list.contains(posting2CommentRelationship2) mustBe true
              val deleteFuture2 = defaultPosting2CommentRelationshipDAO.deleteById(Posting2CommentRelationship2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultPosting2CommentRelationshipDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => {
                      list.size mustBe 1
                      val deleteFuture3 = defaultPosting2CommentRelationshipDAO.deleteById(Posting2CommentRelationship3Id)
                      await(deleteFuture3)
                      deleteFuture3 onComplete {
                        case Success(n) => {
                          n mustBe 1
                          val listFuture2 = defaultPosting2CommentRelationshipDAO.findAll()
                          await(listFuture2)
                          listFuture2 onComplete {
                            case Success(list) => list.size mustBe 0
                            case Failure(t) => fail("failed to retrieve Posting2CommentRelationship list for third time " + t)
                          }
                        }
                        case Failure(t) => fail("failed to delete third Posting2CommentRelationship " + t)
                      }
                    }
                    case Failure(t) => fail("failed to retrieve Posting2CommentRelationship list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second Posting2CommentRelationship " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of Posting2CommentRelationships when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first Posting2CommentRelationship " + t)
      }
    }
    
  }

}