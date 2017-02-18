package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.testutil.TestModelGenerator

class DefaultPersonDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val person1 = TestModelGenerator.getPerson(1)
  val person2 = TestModelGenerator.getPerson(2, image=None)
  val updatedPerson1 = person1.copy(name="updated name")
  lazy val defaultPersonDAO: DefaultPersonDAO = app.injector.instanceOf[DefaultPersonDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPersonDAO..." should {
    
    "store a new PersonModel in the DB when create is called" in {
      val f = defaultPersonDAO.create(person1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new PersonModel " + t)
      }
    }
    
    "find the correct PersonModel from the DB when find is called" in {
      delay
      val retrievedPerson = defaultPersonDAO.findById(person1.id)
      await(retrievedPerson)
      retrievedPerson onComplete {
        case Success(option) => option.get mustBe person1
        case Failure(t) => fail("failed to retrieve the person " + t)
      }
    }
    
    "find the list of all PersonModels when findAll is called" in {
      delay
      val saveFuture = defaultPersonDAO.create(person2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          val listFuture = defaultPersonDAO.findAll()
          await(listFuture)
          listFuture onComplete {
            case Success(list) => list.contains(person1) mustBe true; list.contains(person2) mustBe true
            case Failure(t) => fail("failed to retrieve list of persons " + t)
          }
        }
        case Failure(t) => fail("failed to save second person in DB " + t)
      }
    }
    
    "find the correct PersonModel from the DB when findByUserId is called" in {
      delay
      val retrievedPerson = defaultPersonDAO.findByUserId(person1.userId)
      await(retrievedPerson)
      retrievedPerson onComplete {
        case Success(option) => option.get mustBe person1
        case Failure(t) => fail("failed to retrieve the person " + t)
      }
    }
    
    "update a person when update is called" in {
      delay
      val updateFuture = defaultPersonDAO.update(updatedPerson1)
      await(updateFuture)
      updateFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val updatedPersonFuture = defaultPersonDAO.findById(person1.id)
          await(updatedPersonFuture)
          updatedPersonFuture onComplete {
            case Success(personOption) => personOption.get.name mustBe updatedPerson1.name
            case Failure(t) => fail("failed to retrieve updated person " + t)
          }
        }
        case Failure(t) => fail("failed to update person " + t)
      }
    }
    
    "delete a person when deleteById is called" in {
      delay
      val deleteFuture = defaultPersonDAO.deleteById(person1.id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingPersonListFuture = defaultPersonDAO.findAll()
          await(existingPersonListFuture)
          existingPersonListFuture onComplete {
            case Success(list) => {
              list.contains(person1) mustBe false
              list.contains(person2) mustBe true
              val deleteFuture2 = defaultPersonDAO.deleteById(person2.id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultPersonDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => list.size mustBe 0
                    case Failure(t) => fail("failed to retrieve person list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second person " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of persons when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first person " + t)
      }
    }
    
  }
  
}