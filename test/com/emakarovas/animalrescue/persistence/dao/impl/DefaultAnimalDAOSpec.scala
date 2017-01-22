package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerTest

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.animal.AnimalType
import com.emakarovas.animalrescue.model.common.Gender
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultAnimalDAOSpec extends DelayedPlaySpec with OneAppPerTest {
  
  val Animal1Id = "animal id"
  val Animal1Type = AnimalType.Bird
  val Animal1SpecificType = Some("Parrot")
  val Animal1Name = Some("Birdy")
  val Animal1Gender = Gender.Unspecified
  val Animal1Age = Some(24)
  val Animal1Description = Some("text")
  
  val Animal2Id = "animal2 id"
  val Animal2Type = AnimalType.Dog
  val Animal2SpecificType = None
  val Animal2Name = None
  val Animal2Gender = Gender.Male
  val Animal2Age = None
  val Animal2Description = None
  
  val Animal2NameUpdated = Some("Lassie")

  val animal1 = new AnimalModel(Animal1Id, Animal1Type, Animal1SpecificType, Animal1Name, Animal1Gender, Animal1Age, Animal1Description)
 // val animal2 = new AnimalModel(Animal2Id, Animal2Type, Animal2SpecificType, Animal2Name, Animal2Gender, Animal2Age, Animal2Description)
  val updatedAnimal = new AnimalModel(Animal2Id, Animal2Type, Animal2SpecificType, Animal2NameUpdated, Animal2Gender, Animal2Age, Animal2Description)
  
  lazy val defaultAnimalDAO: DefaultAnimalDAO = app.injector.instanceOf[DefaultAnimalDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultAnimalDAO" should {
    
    "store a new AnimalModel in the DB when create is called" in {
      val f = defaultAnimalDAO.create(animal1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new AnimalModel " + t)
      }
    }
    
    "find the correct AnimalModel from the DB when find is called" in {
      delay
      val retrievedAnimal = defaultAnimalDAO.findById(Animal1Id)
      await(retrievedAnimal)
      retrievedAnimal onComplete {
        case Success(option) => println(option); option.get mustBe animal1
        case Failure(t) => fail("failed to retrieve the AnimalModel " + t)
      }
    }
    
//    "correctly store a new AnimalModel in the DB when create is called, even with some Nones" in {
//      delay
//      val f = defaultAnimalDAO.create(animal2)
//      await(f)
//      f onComplete {
//        case Success(n) => n mustBe 1
//        case Failure(t) => fail("failed to create new AnimalModel " + t)
//      }
//    }
//    
//    "find the correct AnimalModel from the DB when find is called, even with some Nones" in {
//      delay
//      val retrievedAnimal = defaultAnimalDAO.findById(Animal2Id)
//      await(retrievedAnimal)
//      retrievedAnimal onComplete {
//        case Success(option) => println(option); option.get mustBe animal2; option.get.description mustBe None
//        case Failure(t) => fail("failed to retrieve the AnimalModel " + t)
//      }
//    }
//    
//    "find the list of all AnimalModels when findAll is called" in {
//      delay
//      val listFuture = defaultAnimalDAO.findAll()
//      await(listFuture)
//      listFuture onComplete {
//        case Success(list) => println(list); list.contains(animal1) mustBe true; list.contains(animal2) mustBe true
//        case Failure(t) => fail("failed to retrieve list of AnimalModels " + t)
//      }
//    }
//    
//    "update an AnimalModel when update is called" in {
//      delay
//      val updateFuture = defaultAnimalDAO.update(updatedAnimal)
//      await(updateFuture)
//      updateFuture onComplete {
//        case Success(n) => {
//          n mustBe 1
//          val updatedAnimalFuture = defaultAnimalDAO.findById(Animal2Id)
//          await(updatedAnimalFuture)
//          updatedAnimalFuture onComplete {
//            case Success(animalOption) => println(animalOption); animalOption.get.name mustBe Animal2NameUpdated
//            case Failure(t) => fail("failed to retrieve updated AnimalModel " + t)
//          }
//        }
//        case Failure(t) => fail("failed to update AnimalModel " + t)
//      }
//    }
//    
//    "delete an AnimalModel when deleteById is called" in {
//      delay
//      val deleteFuture = defaultAnimalDAO.deleteById(Animal1Id)
//      await(deleteFuture)
//      deleteFuture onComplete {
//        case Success(n) => {
//          n mustBe 1
//          val existingAnimalListFuture = defaultAnimalDAO.findAll()
//          await(existingAnimalListFuture)
//          existingAnimalListFuture onComplete {
//            case Success(list) => {
//              list.contains(animal1) mustBe false
//              list.contains(animal2) mustBe true
//              val deleteFuture2 = defaultAnimalDAO.deleteById(Animal2Id)
//              await(deleteFuture2)
//              deleteFuture2 onComplete {
//                case Success(n) => {
//                  n mustBe 1
//                  val listFuture = defaultAnimalDAO.findAll()
//                  await(listFuture)
//                  listFuture onComplete {
//                    case Success(list) => list.size mustBe 0
//                    case Failure(t) => fail("failed to retrieve AnimalModel list for second time " + t)
//                  }
//                }
//                case Failure(t) => fail("failed to delete second AnimalModel " + t)
//              }
//            }
//            case Failure(t) => fail("failed to retrieve list of AnimalModels when testing delete " + t)
//          }
//        }
//        case Failure(t) => fail("failed to delete first AnimalModel " + t)
//      }
//    }
    
  }

}