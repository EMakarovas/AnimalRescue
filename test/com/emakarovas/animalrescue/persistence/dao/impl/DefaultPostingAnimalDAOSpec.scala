package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.PostingAnimalModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.ImageModel

class DefaultPostingAnimalDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val PostingAnimal1Id = "animal id"
  val PostingAnimal1Type = AnimalType.Bird
  val PostingAnimal1SpecificType = Some("Parrot")
  val PostingAnimal1Name = Some("Birdy")
  val PostingAnimal1Gender = Gender.Female
  val PostingAnimal1Age = Some(24)
  val PostingAnimal1Description = Some("text")
  val PostingAnimal1Image = Some(ImageModel("image id", "url"))
  val PostingAnimal1PostingId = "posting id"
  
  val PostingAnimal2Id = "animal2 id"
  val PostingAnimal2Type = AnimalType.Dog
  val PostingAnimal2SpecificType = None
  val PostingAnimal2Name = None
  val PostingAnimal2Gender = Gender.Male
  val PostingAnimal2Age = None
  val PostingAnimal2Description = None
  val PostingAnimal2Image = Some(ImageModel("image 2 id", "url 2"))
  val PostingAnimal2PostingId = "posting id 2"
  
  val PostingAnimal2NameUpdated = Some("Lassie")

  val animal1 = PostingAnimalModel(PostingAnimal1Id, PostingAnimal1Type, PostingAnimal1SpecificType, PostingAnimal1Name, PostingAnimal1Gender, PostingAnimal1Age, PostingAnimal1Description, PostingAnimal1Image, PostingAnimal1PostingId)
  val animal2 = PostingAnimalModel(PostingAnimal2Id, PostingAnimal2Type, PostingAnimal2SpecificType, PostingAnimal2Name, PostingAnimal2Gender, PostingAnimal2Age, PostingAnimal2Description, PostingAnimal2Image, PostingAnimal2PostingId)
  val updatedPostingAnimal = PostingAnimalModel(PostingAnimal2Id, PostingAnimal2Type, PostingAnimal2SpecificType, PostingAnimal2NameUpdated, PostingAnimal2Gender, PostingAnimal2Age, PostingAnimal2Description, PostingAnimal2Image, PostingAnimal2PostingId)
  
  lazy val defaultPostingAnimalDAO: DefaultPostingAnimalDAO = app.injector.instanceOf[DefaultPostingAnimalDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPostingAnimalDAO" should {
    
    "store a new PostingAnimalModel in the DB when create is called" in {
      val f = defaultPostingAnimalDAO.create(animal1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new PostingAnimalModel " + t)
      }
    }
    
    "correctly store a new PostingAnimalModel in the DB when create is called, even with some Nones" in {
      delay
      val f = defaultPostingAnimalDAO.create(animal2)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new PostingAnimalModel " + t)
      }
    }
    
    "find the correct PostingAnimalModel from the DB when find is called" in {
      delay
      val retrievedPostingAnimal = defaultPostingAnimalDAO.findById(PostingAnimal1Id)
      await(retrievedPostingAnimal)
      retrievedPostingAnimal onComplete {
        case Success(option) => option.get mustBe animal1
        case Failure(t) => fail("failed to retrieve the PostingAnimalModel " + t)
      }
    }
    
    "find the correct PostingAnimalModel from the DB when find is called, even with some Nones" in {
      delay
      val retrievedPostingAnimal = defaultPostingAnimalDAO.findById(PostingAnimal2Id)
      await(retrievedPostingAnimal)
      retrievedPostingAnimal onComplete {
        case Success(option) => option.get mustBe animal2; option.get.description mustBe None
        case Failure(t) => fail("failed to retrieve the PostingAnimalModel " + t)
      }
    }
    
    "find the correct PostingAnimalModel from the DB when findByPostingId is called" in {
      delay
      val retrievedPostingAnimal = defaultPostingAnimalDAO.findByPostingId(PostingAnimal1PostingId)
      await(retrievedPostingAnimal)
      retrievedPostingAnimal onComplete {
        case Success(list) => list.size mustBe 1; list.contains(animal1) mustBe true
        case Failure(t) => fail("failed to retrieve the PostingAnimalModel " + t)
      }
    }

    "find the list of all PostingAnimalModels when findAll is called" in {
      delay
      val listFuture = defaultPostingAnimalDAO.findAll()
      await(listFuture)
      listFuture onComplete {
        case Success(list) => list.contains(animal1) mustBe true; list.contains(animal2) mustBe true
        case Failure(t) => fail("failed to retrieve list of PostingAnimalModels " + t)
      }
    }
    
    "update a PostingAnimalModel when update is called" in {
      delay
      val updateFuture = defaultPostingAnimalDAO.update(updatedPostingAnimal)
      await(updateFuture)
      updateFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val updatedPostingAnimalFuture = defaultPostingAnimalDAO.findById(PostingAnimal2Id)
          await(updatedPostingAnimalFuture)
          updatedPostingAnimalFuture onComplete {
            case Success(animalOption) => animalOption.get.name mustBe PostingAnimal2NameUpdated
            case Failure(t) => fail("failed to retrieve updated PostingAnimalModel " + t)
          }
        }
        case Failure(t) => fail("failed to update PostingAnimalModel " + t)
      }
    }
    
    "delete a PostingAnimalModel when deleteById is called" in {
      delay
      val deleteFuture = defaultPostingAnimalDAO.deleteById(PostingAnimal1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingPostingAnimalListFuture = defaultPostingAnimalDAO.findAll()
          await(existingPostingAnimalListFuture)
          existingPostingAnimalListFuture onComplete {
            case Success(list) => {
              list.contains(animal1) mustBe false
              list.contains(updatedPostingAnimal) mustBe true
              val deleteFuture2 = defaultPostingAnimalDAO.deleteById(PostingAnimal2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultPostingAnimalDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => list.size mustBe 0
                    case Failure(t) => fail("failed to retrieve PostingAnimalModel list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second PostingAnimalModel " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of PostingAnimalModels when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first PostingAnimalModel " + t)
      }
    }
    
  }

}