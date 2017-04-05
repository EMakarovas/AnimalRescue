package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.AnimalTypeDetails
import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.Geolocation
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.Location
import com.emakarovas.animalrescue.model.OfferAnimalModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.OfferTerminationReasonModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.emakarovas.animalrescue.model.property.OfferDeletableCollectionProperty
import com.emakarovas.animalrescue.model.property.OfferDeletableNestedCollectionProperty
import com.emakarovas.animalrescue.model.property.OfferInsertableCollectionProperty
import com.emakarovas.animalrescue.model.property.OfferInsertableNestedCollectionProperty
import com.emakarovas.animalrescue.model.property.OfferUpdatableCollectionProperty
import com.emakarovas.animalrescue.model.property.OfferUpdatableProperty
import com.emakarovas.animalrescue.persistence.dao.update.VersionedModelContainer
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec
import com.emakarovas.animalrescue.testutil.TestUtils

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout
import scala.collection.mutable.ArrayBuffer

class DefaultOfferDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  private val CommonUserId = "user id 1"
  private var offer1 = OfferModel("id 1", "url 1", TestUtils.buildDate(1, 1, 2011), Some(TestUtils.buildDate(2, 2, 2012)),
      "offer text 1", 
      List(
          OfferAnimalModel("offer animal id 1", 
              AnimalTypeDetails(AnimalType.Pig, Set(SpecificType.PotBelliedPig, SpecificType.MiniaturePig)),
              Some(Gender.Female), Some("pinkie 11"), 11, Some("description 1"), 
              Set(Color.Pink), Size.Small, Set("Cute", "Fat"), true, 
              Some(ImageModel("img id 1", "img url 1")), Some(VideoModel("vid id 1", "vid url 1")),
              Some(11.1), Some(12.1), Some(13.1), Some(14.1),
              Some(OfferTerminationReasonModel(OfferTerminationReason.Other, Some("text 1")))),
          OfferAnimalModel("offer animal id 11", 
              AnimalTypeDetails(AnimalType.Pig, Set()),
              Some(Gender.Female), Some("other pig 21"), 15, Some("description 11"),
              Set(Color.Brown), Size.Medium, Set("Evil", "Noisy"), false,
              Some(ImageModel("img id 11", "img url 11")), Some(VideoModel("vid id 11", "vid url 11")),
              Some(1111.11), Some(112.11), Some(113.11), Some(114.11),
              Some(OfferTerminationReasonModel(OfferTerminationReason.Other, Some("text 11"))))),
      Some(ImageModel("img id 1", "img url 1")), Some(VideoModel("vid id 1", "vid url 1")),
      Location("country 1", "city 1", Some("street 1"), Geolocation(15.5, 16.5)),
      List(
          CommentModel("comment id 1", TestUtils.buildDate(3, 3, 2013), "comment text 1", Some("comment name 1"), 
          Some("comment user id 1")),
          CommentModel("comment id 2", TestUtils.buildDate(4, 4, 2014), "comment text 2", Some("comment name 2"), 
          Some("comment user id 2"))),
      Set("viewed by user 1", "viewed by user 2"), CommonUserId)
  private var offer2 = OfferModel("id 2", "url 2", TestUtils.buildDate(2, 2, 2022), None,
      "offer text 2", 
      List(),
      None, None,
      Location("country 2", "city 2", None, Geolocation(25.5, 26.5)),
      List(),
      Set(), "some other user id")
  private var offer3 = OfferModel("id 3", "url 3", TestUtils.buildDate(3, 3, 2033), Some(TestUtils.buildDate(2, 2, 2032)),
      "offer text 3", 
      List(
          OfferAnimalModel("offer animal id 3", 
              AnimalTypeDetails(AnimalType.Dog, Set(SpecificType.CockerSpaniel)),
              None, None, 33, Some("description 3"), 
              Set(Color.Black), Size.Medium, Set(), false, 
              Some(ImageModel("img id 3", "img url 3")), Some(VideoModel("vid id 3", "vid url 3")),
              Some(33.3), Some(32.3), Some(33.3), Some(34.3),
              Some(OfferTerminationReasonModel(OfferTerminationReason.Other, Some("text 3")))),
          OfferAnimalModel("offer animal id 33", 
              AnimalTypeDetails(AnimalType.Dog, Set()),
              Some(Gender.Female), Some("other pig 23"), 35, Some("description 33"),
              Set(Color.Brown), Size.Medium, Set("Evil", "Noisy"), false,
              Some(ImageModel("img id 33", "img url 33")), Some(VideoModel("vid id 33", "vid url 33")),
              Some(3333.33), Some(332.33), Some(333.33), Some(334.33),
              Some(OfferTerminationReasonModel(OfferTerminationReason.Other, Some("text 33"))))),
      Some(ImageModel("img id 3", "img url 3")), Some(VideoModel("vid id 3", "vid url 3")),
      Location("country 3", "city 3", Some("street 3"), Geolocation(35.5, 36.5)),
      List(
          CommentModel("comment id 3", TestUtils.buildDate(3, 3, 2033), "comment text 3", Some("comment name 3"), 
          Some("comment user id 3")),
          CommentModel("comment id 2", TestUtils.buildDate(4, 4, 2034), "comment text 2", Some("comment name 2"), 
          Some("comment user id 2"))),
      Set("viewed by user 3", "viewed by user 2"), CommonUserId)

  lazy val defaultOfferDAO = app.injector.instanceOf[DefaultOfferDAO]
    
  import scala.concurrent.ExecutionContext.Implicits.global

  "DefaultOfferDAOSpec" should {
    
    "create a new OfferModel by calling create()" in {
      val crF = defaultOfferDAO.create(offer1)
      await(crF)
      crF onSuccess {
        case n => n mustBe 1
      }
      val findF = crF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe offer1
      }
    }
    
    "create a new OfferModel by calling create(), even with some Nones" in {
      val crF = defaultOfferDAO.create(offer2)
      await(crF)
      crF onSuccess {
        case n => n mustBe 1
      }
      val findF = crF.flatMap(_ => defaultOfferDAO.findById(offer2.id))
      await(findF)
      findF onSuccess {
        case offerOpt => offerOpt.get mustBe offer2
      }
    }
    
    "find a list of all OfferModels when findAll(count) is called" in {
      delay()
      val saveF = defaultOfferDAO.create(offer3)
      await(saveF)
      val findF = saveF.flatMap(_ => defaultOfferDAO.findAll(250))
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 3
          list.contains(offer1) mustBe true
          list.contains(offer2) mustBe true
          list.contains(offer3) mustBe true
        }
      }
      val findF2 = defaultOfferDAO.findAll(2)
      await(findF2)
      findF2 onSuccess {
        case list => {
          list.size mustBe 2
          list.contains(offer1) mustBe true
          list.contains(offer2) mustBe true
        }
      }
    }
    
    "allow to update an OfferModel if no properties have been updated individually" in {
      delay()
      val findF = defaultOfferDAO.findUpdatableById(offer1.id)
      await(findF)
      val updF = findF.flatMap(updateOpt => {
        updateOpt.isDefined mustBe true
        val offer = updateOpt.get.model
        offer mustBe offer1
        offer1 = offer1.copy(text="updated text 1")
        defaultOfferDAO.update(VersionedModelContainer[OfferModel](offer1, updateOpt.get.version))
      })
      await(updF)
      val updatedFindF = updF.flatMap(n => {
        n mustBe 1
        defaultOfferDAO.findById(offer1.id)
      })
      await(updatedFindF)
      updatedFindF onSuccess {
        case opt => {
          opt.isDefined mustBe true
          opt.get mustBe offer1
        }
      }
    }
    
    "update OfferUpdatableProperty's that are not None's" in {
      delay()
      
      offer1 = offer1.copy(
          text="updated once again",
          image=Some(ImageModel("updated img id 1", "updated img url 1")),
          video=Some(VideoModel("updated vid id 1", "updated vid url 1"))
      )
      
      val textProp = OfferUpdatableProperty.Text(offer1.text)
      val updF1 = defaultOfferDAO.updatePropertyById(offer1.id, textProp)
      
      val imgProp = OfferUpdatableProperty.Image(offer1.image)
      val updF2 = defaultOfferDAO.updatePropertyById(offer1.id, imgProp)
      
      val vidProp = OfferUpdatableProperty.Video(offer1.video)
      val updF3 = defaultOfferDAO.updatePropertyById(offer1.id, vidProp)
      
      val compF = for {
        u1 <- updF1
        u2 <- updF2
        u3 <- updF3
      } yield(u1 + u2 + u3)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op: Option[OfferModel] => {
          op.get mustBe offer1
        }
      }

    }
    
    "update OfferUpdatableProperty's that are None's" in {
      delay()
      
      offer1 = offer1.copy(
          image = None,
          video = None
      )
      
      val imgProp = OfferUpdatableProperty.Image(offer1.image)
      val updF1 = defaultOfferDAO.updatePropertyById(offer1.id, imgProp)
      
      val vidProp = OfferUpdatableProperty.Video(offer1.video)
      val updF2 = defaultOfferDAO.updatePropertyById(offer1.id, vidProp)
      
      val compF = for {
        u1 <- updF1
        u2 <- updF2
      } yield(u1 + u2)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op: Option[OfferModel] => {
          op.get mustBe offer1
        }
      }

    }

    "deny update request when a property has been updated during model manipulation" in {
      delay()
      // find the model and lock it
      val findF = defaultOfferDAO.findUpdatableById(offer1.id)
      await(findF)
      // change model and issue a property update
      offer1 = offer1.copy(text="updated for the billionth time")
      defaultOfferDAO.updatePropertyById(offer1.id, OfferUpdatableProperty.Text(offer1.text))
      // try to update
      val modelUpdateF = findF.flatMap(
          op => {
            val token = op.get.version
            defaultOfferDAO.update(VersionedModelContainer[OfferModel](offer2, token))
          }
      )
      await(modelUpdateF)
      modelUpdateF onSuccess {
        case n => n mustBe 0
      }

    }
    
    "insert OfferInsertableCollectionProperty's" in {
      delay()
      val newComment = CommentModel("comment id 155", TestUtils.buildDate(3, 3, 2018), "comment text 14", Some("comment name 12"), 
          Some("comment user id 15555"))
      val newAnimal = OfferAnimalModel("offer animal id 888", 
              AnimalTypeDetails(AnimalType.Pig, Set()),
              Some(Gender.Male), Some("pinkie 71"), 11, Some("description 1333"), 
              Set(), Size.Small, Set(), false, 
              Some(ImageModel("img id 1", "img url 1")), Some(VideoModel("vid id 1", "vid url 1")),
              Some(11.1), Some(12.1), Some(13.1), Some(14.1),
              Some(OfferTerminationReasonModel(OfferTerminationReason.Other, Some("text 1"))))
      offer1 = offer1.copy(
          commentList = offer1.commentList ::: List(newComment),
          offerAnimalList = offer1.offerAnimalList ::: List(newAnimal)
      )
      
      val comProp = OfferInsertableCollectionProperty.Comment(newComment)
      val updF = defaultOfferDAO.insertCollectionPropertyById(offer1.id, comProp)
      updF onSuccess {
        case n => n mustBe 1
      }
      
      val aniProp = OfferInsertableCollectionProperty.OfferAnimal(newAnimal)
      val updF2 = defaultOfferDAO.insertCollectionPropertyById(offer1.id, aniProp)
      updF2 onSuccess {
        case n => n mustBe 1
      }
      
      val allF = for {
        u1 <- updF
        u2 <- updF2
      } yield(u1 + u2)
      
      await(allF)
      val findF = allF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe offer1
      }
      
    }
    
    "delete OfferDeletableCollectionProperty's" in {
      delay()
      
      val deletedAnimalId = offer1.offerAnimalList(0).id
      offer1 = offer1.copy(
          offerAnimalList = offer1.offerAnimalList.slice(1, offer1.offerAnimalList.size)
      )
      
      val prop = OfferDeletableCollectionProperty.OfferAnimal(deletedAnimalId)
      val updF = defaultOfferDAO.deleteCollectionPropertyById(offer1.id, prop)
      await(updF)
      updF onSuccess {
        case n => n mustBe 1
      }
      val findF = updF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe offer1
      }
    }
    
    "not delete anything when an invalid OfferDeletableCollectionProperty is passed" in {
      delay()
      
      val prop = OfferDeletableCollectionProperty.OfferAnimal("some random id")
      val updF = defaultOfferDAO.deleteCollectionPropertyById(offer1.id, prop)
      await(updF)
      updF onSuccess {
        case n => n mustBe 0
      }
      val findF = updF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe offer1
      }
    }
    
    "update OfferUpdatableCollectionProperty's that are not None's" in {
      delay()
      
      val oldAnimal = offer1.offerAnimalList(0)
      val updatedOfferAnimal = oldAnimal.copy(
          gender = Some(Gender.Male),
          name = Some("updated name"),
          age = 777,
          description = Some("updated description 789"),
          size = Size.Large,
          isCastrated = false,
          image = Some(ImageModel("updated id", "updated url")),
          video = Some(VideoModel("updated id", "updated url")),
          castrationCost = Some(88.8),
          foodCost = Some(88.8),
          shelterCost = Some(88.8),
          vaccinationCost = Some(88.8)
      )
      val oldList = offer1.offerAnimalList
      val newList = List(updatedOfferAnimal) ::: oldList.slice(1, oldList.size)
      
      offer1 = offer1.copy(
          offerAnimalList = newList
      )
      
      val genProp = OfferUpdatableCollectionProperty.OfferAnimalGender(
          updatedOfferAnimal.id, updatedOfferAnimal.gender)
      val updF1 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, genProp)
      
      val nameProp = OfferUpdatableCollectionProperty.OfferAnimalName(
          updatedOfferAnimal.id, updatedOfferAnimal.name)
      val updF2 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, nameProp)
      
      val ageProp = OfferUpdatableCollectionProperty.OfferAnimalAge(
          updatedOfferAnimal.id, updatedOfferAnimal.age)
      val updF3 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, ageProp)
      
      val descProp = OfferUpdatableCollectionProperty.OfferAnimalDescription(
          updatedOfferAnimal.id, updatedOfferAnimal.description)
      val updF4 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, descProp)
      
      val sizeProp = OfferUpdatableCollectionProperty.OfferAnimalSize(
          updatedOfferAnimal.id, updatedOfferAnimal.size)
      val updF5 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, sizeProp)
      
      val castProp = OfferUpdatableCollectionProperty.OfferAnimalIsCastrated(
          updatedOfferAnimal.id, updatedOfferAnimal.isCastrated)
      val updF6 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, castProp)
      
      val imgProp = OfferUpdatableCollectionProperty.OfferAnimalImage(
          updatedOfferAnimal.id, updatedOfferAnimal.image)
      val updF7 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, imgProp)
      
      val vidProp = OfferUpdatableCollectionProperty.OfferAnimalVideo(
          updatedOfferAnimal.id, updatedOfferAnimal.video)
      val updF8 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, vidProp)
      
      val castCostProp = OfferUpdatableCollectionProperty.OfferAnimalCastrationCost(
          updatedOfferAnimal.id, updatedOfferAnimal.castrationCost)
      val updF9 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, castCostProp)
      
      val foodCostProp = OfferUpdatableCollectionProperty.OfferAnimalFoodCost(
          updatedOfferAnimal.id, updatedOfferAnimal.foodCost)
      val updF10 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, foodCostProp)
      
      val shelCostProp = OfferUpdatableCollectionProperty.OfferAnimalShelterCost(
          updatedOfferAnimal.id, updatedOfferAnimal.shelterCost)
      val updF11 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, shelCostProp)
      
      val vacCostProp = OfferUpdatableCollectionProperty.OfferAnimalVaccinationCost(
          updatedOfferAnimal.id, updatedOfferAnimal.vaccinationCost)
      val updF12 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, vacCostProp)
      
      val compF = for {
        f1 <- updF1
        f2 <- updF2
        f3 <- updF3
        f4 <- updF4
        f5 <- updF5
        f6 <- updF6
        f7 <- updF7
        f8 <- updF8
        f9 <- updF9
        f10 <- updF10
        f11 <- updF11
        f12 <- updF12
      } yield(f1 + f2 + f3 + f4 + f5 + f6 + f7 + f8 + f9 + f10 + f11 + f12)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe offer1
      }
    }
    
    "update OfferUpdatableCollectionProperty's that are None's" in {
      delay()
      
      val oldAnimal = offer1.offerAnimalList(0)
      val updatedOfferAnimal = oldAnimal.copy(
          gender = Some(Gender.Male),
          name = Some("updated name"),
          description = Some("updated description 789"),
          image = Some(ImageModel("updated id", "updated url")),
          video = Some(VideoModel("updated id", "updated url")),
          castrationCost = Some(88.8),
          foodCost = Some(88.8),
          shelterCost = Some(88.8),
          vaccinationCost = Some(88.8)
      )
      val oldList = offer1.offerAnimalList
      val newList = List(updatedOfferAnimal) ::: oldList.slice(1, oldList.size)
      
      offer1 = offer1.copy(
          offerAnimalList = newList
      )
      
      val genProp = OfferUpdatableCollectionProperty.OfferAnimalGender(
          updatedOfferAnimal.id, updatedOfferAnimal.gender)
      val updF1 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, genProp)
      
      val nameProp = OfferUpdatableCollectionProperty.OfferAnimalName(
          updatedOfferAnimal.id, updatedOfferAnimal.name)
      val updF2 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, nameProp)
      
      val descProp = OfferUpdatableCollectionProperty.OfferAnimalDescription(
          updatedOfferAnimal.id, updatedOfferAnimal.description)
      val updF3 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, descProp)

      val imgProp = OfferUpdatableCollectionProperty.OfferAnimalImage(
          updatedOfferAnimal.id, updatedOfferAnimal.image)
      val updF4 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, imgProp)
      
      val vidProp = OfferUpdatableCollectionProperty.OfferAnimalVideo(
          updatedOfferAnimal.id, updatedOfferAnimal.video)
      val updF5 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, vidProp)
      
      val castCostProp = OfferUpdatableCollectionProperty.OfferAnimalCastrationCost(
          updatedOfferAnimal.id, updatedOfferAnimal.castrationCost)
      val updF6 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, castCostProp)
      
      val foodCostProp = OfferUpdatableCollectionProperty.OfferAnimalFoodCost(
          updatedOfferAnimal.id, updatedOfferAnimal.foodCost)
      val updF7 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, foodCostProp)
      
      val shelCostProp = OfferUpdatableCollectionProperty.OfferAnimalShelterCost(
          updatedOfferAnimal.id, updatedOfferAnimal.shelterCost)
      val updF8 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, shelCostProp)
      
      val vacCostProp = OfferUpdatableCollectionProperty.OfferAnimalVaccinationCost(
          updatedOfferAnimal.id, updatedOfferAnimal.vaccinationCost)
      val updF9 = defaultOfferDAO.updateCollectionPropertyById(offer1.id, vacCostProp)
      
      val compF = for {
        f1 <- updF1
        f2 <- updF2
        f3 <- updF3
        f4 <- updF4
        f5 <- updF5
        f6 <- updF6
        f7 <- updF7
        f8 <- updF8
        f9 <- updF9
      } yield(f1 + f2 + f3 + f4 + f5 + f6 + f7 + f8 + f9)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe offer1
      }
    }
    
    "insert OfferInsertableNestedCollectionProperty's" in {
      delay()
      
      val oldAnimal = offer1.offerAnimalList(0)
      val oldColorSet = oldAnimal.colorSet
      val newColor = Color.Golden
      val newColorSet = oldColorSet + newColor
      val oldTagSet = oldAnimal.tagSet
      val newTag = "Some tag"
      val newTagSet = oldTagSet + newTag
      val updatedOfferAnimal = oldAnimal.copy(
          colorSet = newColorSet,
          tagSet = newTagSet
      )
      val oldList = offer1.offerAnimalList
      val newList = List(updatedOfferAnimal) ::: oldList.slice(1, oldList.size)
      
      offer1 = offer1.copy(
          offerAnimalList = newList
      )
      
      val colProp = OfferInsertableNestedCollectionProperty.OfferAnimalColor(oldAnimal.id, newColor)
      val updF1 = defaultOfferDAO.insertNestedCollectionPropertyById(offer1.id, colProp)
      updF1 onSuccess {
        case n => n mustBe 1
      }
      
      val tagProp = OfferInsertableNestedCollectionProperty.OfferAnimalTag(oldAnimal.id, newTag)
      val updF2 = defaultOfferDAO.insertNestedCollectionPropertyById(offer1.id, tagProp)
      updF2 onSuccess {
        case n => n mustBe 1
      }
      
      val compF = for {
        f1 <- updF1
        f2 <- updF2
      } yield(f1 + f2)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe offer1
      }
    }
    
    "delete OfferDeletableNestedCollectionProperty's" in {
      delay()
      
      val oldAnimal = offer1.offerAnimalList(0)
      val oldColorSet = oldAnimal.colorSet
      val deletedColor = oldColorSet.head
      val newColorSet = oldColorSet - deletedColor
      val oldTagSet = oldAnimal.tagSet
      val deletedTag = oldTagSet.head
      val newTagSet = oldTagSet - deletedTag
      val updatedOfferAnimal = oldAnimal.copy(
          colorSet = newColorSet,
          tagSet = newTagSet
      )
      val oldList = offer1.offerAnimalList
      val newList = List(updatedOfferAnimal) ::: oldList.slice(1, oldList.size)
      
      offer1 = offer1.copy(
          offerAnimalList = newList
      )
      
      val colProp = OfferDeletableNestedCollectionProperty.OfferAnimalColor(oldAnimal.id, deletedColor)
      val updF1 = defaultOfferDAO.deleteNestedCollectionPropertyById(offer1.id, colProp)
      updF1 onSuccess {
        case n => n mustBe 1
      }
      
      val tagProp = OfferDeletableNestedCollectionProperty.OfferAnimalTag(oldAnimal.id, deletedTag)
      val updF2 = defaultOfferDAO.deleteNestedCollectionPropertyById(offer1.id, tagProp)
      updF2 onSuccess {
        case n => n mustBe 1
      }
      
      val compF = for {
        f1 <- updF1
        f2 <- updF2
      } yield(f1 + f2)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe offer1
      }
    }
    
    "not delete anything when an invalid OfferDeletableNestedCollectionProperty is passed" in {
      delay()
      
      val tagProp = OfferDeletableNestedCollectionProperty.OfferAnimalTag(offer1.offerAnimalList(0).id, "some random tag")
      val updF = defaultOfferDAO.deleteNestedCollectionPropertyById(offer1.id, tagProp)
      await(updF)
      updF onSuccess {
        case n => n mustBe 0
      }
      val findF = updF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe offer1
      }
    }
    
    "add a specificType when addSpecificTypeById is called" in {
      delay()
      
      val newSpecificType = SpecificType.Poodle
      val oldAnimal = offer3.offerAnimalList(1)
      val oldTypeDetails = oldAnimal.animalTypeDetails
      val newTypeDetails = oldTypeDetails.copy(
          specificTypeSet = oldTypeDetails.specificTypeSet ++ Set(newSpecificType)
      )
      val newAnimal = oldAnimal.copy(animalTypeDetails = newTypeDetails)
      
      val newAnimalList = List(offer3.offerAnimalList(0)) ::: List(newAnimal)
      
      offer3 = offer3.copy(offerAnimalList = newAnimalList)
      
      val updF = defaultOfferDAO.addSpecificTypeById(offer3.id, newAnimal.id, newSpecificType)
      await(updF)
      updF onSuccess {
        case n => n mustBe 1
      }
      val findF = updF.flatMap(_ => defaultOfferDAO.findById(offer3.id))
      await(findF)
      findF onSuccess {
        case opt => opt.get mustBe offer3
      }
    }
    
    "not update the DB when addSpecificTypeById is called if the specificType already exists, or is not of the same Animal type" in {
      delay()
      
      val animalId = offer3.offerAnimalList(1).id
      
      val updF1 = defaultOfferDAO.addSpecificTypeById(offer3.id, animalId, SpecificType.Poodle) // already contains
      await(updF1)
      updF1 onSuccess {
        case n => n mustBe 0
      }
      
      val updF2 = defaultOfferDAO.addSpecificTypeById(offer3.id, animalId, SpecificType.Parrot)
      await(updF2)
      updF2 onSuccess {
        case n => n mustBe 0
      }
      
      val f = for {
        u1 <- updF1
        u2 <- updF2
      } yield(u1 + u2)
      
      val findF = f.flatMap(_ => defaultOfferDAO.findById(offer3.id))
      await(findF)
      findF onSuccess {
        case opt => opt.get mustBe offer3
      }
      
    }
    
    "delete a specificType when deleteSpecificTypeById is called" in {
      delay()
      
      val oldAnimalList = offer3.offerAnimalList
      val oldAnimal = oldAnimalList(1)
      val deleteSpecificType = SpecificType.Poodle
      val oldTypeDetails = oldAnimal.animalTypeDetails
      val newTypeDetails = oldTypeDetails.copy(
          specificTypeSet = Set[SpecificType[Animal.Dog]]()
      )
      
      val newAnimal = oldAnimal.copy(animalTypeDetails = newTypeDetails)
      val newList = List(oldAnimalList(0)) ::: List(newAnimal)
      offer3 = offer3.copy(offerAnimalList = newList)
            
      val updF = defaultOfferDAO.removeSpecificTypeById(offer3.id, newAnimal.id, deleteSpecificType)
      await(updF)
      updF onSuccess {
        case n => n mustBe 1
      }
      val findF = updF.flatMap(_ => defaultOfferDAO.findById(offer3.id))
      await(findF)
      findF onSuccess {
        case opt => opt.get mustBe offer3
      }
    }
    
    "not delete anything when deleteSpecificTypeById is called with wrong or non-existing SpecificType" in {
      delay()
      
      val animalId = offer3.offerAnimalList(1).id
      val updF = defaultOfferDAO.removeSpecificTypeById(offer3.id, animalId, SpecificType.Poodle)
      await(updF)
      updF onSuccess {
        case n => n mustBe 0
      }
      
      val updF2 = defaultOfferDAO.removeSpecificTypeById(offer3.id, animalId, SpecificType.Parrot)
      await(updF2)
      updF2 onSuccess {
        case n => n mustBe 0
      }
      val findF = Future.sequence(List(updF, updF2)).flatMap(_ => defaultOfferDAO.findById(offer3.id))
      await(findF)
      findF onSuccess {
        case opt => opt.get mustBe offer3
      }
    }
    
    "find corresponding OfferModels with OfferAnimalModels when findByOfferSearch is called" in {
      delay()
      
      val map = scala.collection.mutable.Map[String, List[OfferAnimalModel]]().empty
      val offerList = List(offer1, offer2, offer3)
      for(offer <- offerList; animal <- offer.offerAnimalList) {
        val exists = map.contains(offer.id)
        if(exists) {
          val list = map.get(offer.id).get
          val newList = list ++ List(animal)
          map.put(offer.id, newList) 
        } else {
          map.put(offer.id, List(animal))
        }
      }
    }
    
    "add to viewedBy set when addToViewedByUserIdSetById is called if it doesn't exist yet" in {
      delay()
      val addedId = "added id"
      offer1 = offer1.copy(
          viewedByUserIdSet = offer1.viewedByUserIdSet + addedId
      )
      val addF = defaultOfferDAO.addToViewedByUserIdSetById(offer1.id, addedId)
      await(addF)
      val findF = addF.flatMap(_ => defaultOfferDAO.findById(offer1.id))
      await(findF)
      val addF2 = findF.flatMap(op => {
        op.get mustBe offer1
        defaultOfferDAO.addToViewedByUserIdSetById(offer1.id, addedId)
      })
      await(addF2)
      val findF2 = addF2.flatMap(n => {
        n mustBe 0
        defaultOfferDAO.findById(offer1.id)
      })
      await(findF2)
      findF2 onSuccess {
        // the retrieved offer must be the same, i.e. have the same list length
        case op => op.get mustBe offer1
      }
    }
    
    "find OfferModels when findByUserId() is called" in {
      delay()
      val findF = defaultOfferDAO.findByUserId(CommonUserId)
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 2
          list.contains(offer1) mustBe true
          list.contains(offer3) mustBe true
        }
      }
    }
    
    "delete OfferModels when deleteById() is called" in {
      delay()
      val singleDeleteF = defaultOfferDAO.deleteById(offer1.id)
      await(singleDeleteF)
      val findF = singleDeleteF.flatMap(_ => defaultOfferDAO.findAll(500))
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 2
          list.contains(offer2) mustBe true
          list.contains(offer3) mustBe true
        }
      }
      val delF2 = findF.flatMap(_ => defaultOfferDAO.deleteById(offer2.id))
      val delF3 = findF.flatMap(_ => defaultOfferDAO.deleteById(offer3.id))
      val allDelF = for {
        f2 <- delF2
        f3 <- delF3
      } yield(f2 + f3)
      await(allDelF)
      val findF2 = allDelF.flatMap(_ => defaultOfferDAO.findAll(15))
      await(findF2)
      findF2 onSuccess {
        case list => list.size mustBe 0
      }
    }
    
  }
  
}