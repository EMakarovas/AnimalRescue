package com.emakarovas.animalrescue.persistence.dao.impl

import org.scalatestplus.play.OneAppPerTest
import org.scalatestplus.play.PlaySpec
import play.api.test.Helpers
import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

import com.emakarovas.animalrescue.model.PersonModel
import javax.inject.Inject
import scala.util.Failure
import scala.util.Success

class DefaultPersonDAOSpec extends PlaySpec with OneAppPerTest {
  
  val PersonId = "test id"
  val PersonName = "test name"
  val PersonSurname = "test surname"
  val updatedPersonName = "updated test name"
  val Person2Id = "test id2"
  val Person2Name = "test name2"
  val Person2Surname = "test surname2"
  val person1 = new PersonModel(PersonId, PersonName, PersonSurname)
  val person2 = new PersonModel(Person2Id, Person2Name, Person2Surname)
  val updatedPerson1 = new PersonModel(PersonId, updatedPersonName, PersonSurname)
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
      val retrievedPerson = defaultPersonDAO.findById(PersonId)
      await(retrievedPerson)
      retrievedPerson onComplete {
        case Success(option) => option.get mustBe person1
        case Failure(t) => fail("failed to retrieve the person " + t)
      }
    }
    
    "find the list of all PersonModels when findAll is called" in {
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
    
    "update a person when update is called" in {
      val updateFuture = defaultPersonDAO.update(updatedPerson1)
      await(updateFuture)
      updateFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val updatedPersonFuture = defaultPersonDAO.findById(PersonId)
          await(updatedPersonFuture)
          updatedPersonFuture onComplete {
            case Success(personOption) => personOption.get.name mustBe updatedPersonName
            case Failure(t) => fail("failed to retrieve updated person " + t)
          }
        }
        case Failure(t) => fail("failed to update person " + t)
      }
    }
    
    "delete a person when deleteById is called" in {
      val deleteFuture = defaultPersonDAO.deleteById(PersonId)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingPersonListFuture = defaultPersonDAO.findAll()
          await(existingPersonListFuture)
          existingPersonListFuture onComplete {
            case Success(list) => {
              list.contains(person2) mustBe true
              val deleteFuture2 = defaultPersonDAO.deleteById(Person2Id)
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