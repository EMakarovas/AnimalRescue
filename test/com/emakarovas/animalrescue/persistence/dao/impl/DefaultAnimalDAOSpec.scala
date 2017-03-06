package com.emakarovas.animalrescue.persistence.dao.impl

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.AdoptionDetailsModel
import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.OfferDetailsModel
import com.emakarovas.animalrescue.model.OfferTerminationReasonModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec
import com.emakarovas.animalrescue.testutil.TestUtils
import com.emakarovas.animalrescue.persistence.dao.update.UpdatableModelContainer
import com.emakarovas.animalrescue.model.property.AnimalUpdatableProperty

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout
import com.emakarovas.animalrescue.persistence.dao.update.UpdateStatus

class DefaultAnimalDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  private val CommonOfferId = "offer id 1"
  private val CommonOwnerId = Some("owner id 1")
  private var animal1 = AnimalModel("id 1", AnimalType.Bird, Some("Parrot"), Gender.Male, Some("animal name 1"), Some(12),
      Some("description 1"), Some(ImageModel("img id 1", "img url 1")), Some(VideoModel("vid id 1", "vid url 1")),
      Some(AdoptionDetailsModel(None, TestUtils.buildDate(1, 1, 2011))),
      Some(OfferDetailsModel(Some(1), Some(2), Some(3), CommonOfferId, 
          Some(OfferTerminationReasonModel(OfferTerminationReason.KeptIt, Some("term text 1"))))))
  private var animal2 = AnimalModel("id 2", AnimalType.Cat, None, Gender.Unspecified, None, None,
      None, None, None,
      Some(AdoptionDetailsModel(CommonOwnerId, TestUtils.buildDate(2, 2, 2012))),
      None)
  private var animal3 = AnimalModel("id 3", AnimalType.Dog, Some("Rottweiler"), Gender.Female, None, Some(24),
      None, None, None,
      None,
      Some(OfferDetailsModel(Some(4), Some(5), Some(6), CommonOfferId,
          Some(OfferTerminationReasonModel(OfferTerminationReason.GaveToSomeoneElse, Some("term text 3"))))))
  private var animal4 = AnimalModel("id 4", AnimalType.Pig, Some("Cute pig"), Gender.Unspecified, Some("Junior"), None,
      None, None, None,
      Some(AdoptionDetailsModel(CommonOwnerId, TestUtils.buildDate(4, 4, 2014))),
      None)
  
  lazy val defaultAnimalDAO = app.injector.instanceOf[DefaultAnimalDAO]
    
  import scala.concurrent.ExecutionContext.Implicits.global

  "DefaultAnimalDAOSpec" should {
    
    "create a new AnimalModel by calling create()" in {
      val crF = defaultAnimalDAO.create(animal1)
      await(crF)
      crF onSuccess {
        case writeRes => {
          writeRes mustBe 1
          val findF = defaultAnimalDAO.findById(animal1.id)
          await(findF)
          findF onSuccess {
            case animalOpt => animalOpt.get mustBe animal1
          }
        }
      }
    }
    
    "create a new AnimalModel by calling create(), even with some Nones" in {
      val crF = defaultAnimalDAO.create(animal2)
      await(crF)
      crF onSuccess {
        case writeRes => {
          writeRes mustBe 1
          val findF = defaultAnimalDAO.findById(animal2.id)
          await(findF)
          findF onSuccess {
            case animalOpt => animalOpt.get mustBe animal2
          }
        }
      }
    }
    
    "after calling create(), return the AnimalModel in findById even before the save is done in the DB" in {
      val crF = defaultAnimalDAO.create(animal3)
      val findF = defaultAnimalDAO.findById(animal3.id)
      await(crF)
      await(findF)
      findF onSuccess {
        case opt => {
          opt.isDefined mustBe true
          opt.get mustBe animal3
        }
      }
    }
    
    "allow to update an AnimalModel if no properties have been updated individually" in {
      delay()
      val findF = defaultAnimalDAO.lockAndFindById(animal1.id)
      await(findF)
      val updF = findF.flatMap(updateOpt => {
        updateOpt.isDefined mustBe true
        val animal = updateOpt.get.model
        animal mustBe animal1
        animal1 = animal1.copy(description=Some("updated description 1"))
        defaultAnimalDAO.update(UpdatableModelContainer[AnimalModel](animal1, updateOpt.get.token))
      })
      await(updF)
      val updatedFindF = updF.flatMap(updRes => {
        updRes.updateStatus mustBe UpdateStatus.Executed
        updRes.n mustBe 1
        defaultAnimalDAO.findById(animal1.id)
      })
      await(updatedFindF)
      updatedFindF onSuccess {
        case opt => {
          opt.isDefined mustBe true
          opt.get mustBe animal1
        }
      }
    }
    
    "update AnimalUpdatableProperty's that are not None's" in {
      delay()
      
      animal1 = animal1.copy(
          specificType = Some("spec type"),
          gender = Gender.Female,
          name = Some("updated animal name"),
          age = Some(9001),
          description = Some("updated descr"),
          image = Some(ImageModel("updated id", "updated url")),
          video = Some(VideoModel("updated vid id", "updated vid url")),
          offerDetails = Some(animal1.offerDetails.get.copy(
              foodCost = Some(15),
              shelterCost = Some(16),
              vaccinationCost = Some(17)
          ))
      )
      
      val specificTypeProp = AnimalUpdatableProperty.AnimalSpecificTypeProperty(animal1.specificType)
      val updF1 = defaultAnimalDAO.updatePropertyById(animal1.id, specificTypeProp)
      
      val genderProp = AnimalUpdatableProperty.AnimalGenderProperty(animal1.gender)
      val updF2 = defaultAnimalDAO.updatePropertyById(animal1.id, genderProp)
      
      val nameProp = AnimalUpdatableProperty.AnimalNameProperty(animal1.name)
      val updF3 = defaultAnimalDAO.updatePropertyById(animal1.id, nameProp)
      
      val ageProp = AnimalUpdatableProperty.AnimalAgeProperty(animal1.age)
      val updF4 = defaultAnimalDAO.updatePropertyById(animal1.id, ageProp)
      
      val descriptionProp = AnimalUpdatableProperty.AnimalDescriptionProperty(animal1.description)
      val updF5 = defaultAnimalDAO.updatePropertyById(animal1.id, descriptionProp)
      
      val imageProp = AnimalUpdatableProperty.AnimalImageProperty(animal1.image)
      val updF6 = defaultAnimalDAO.updatePropertyById(animal1.id, imageProp)
      
      val videoProp = AnimalUpdatableProperty.AnimalVideoProperty(animal1.video)
      val updF7 = defaultAnimalDAO.updatePropertyById(animal1.id, videoProp)
      
      val foodCostProp = AnimalUpdatableProperty.AnimalFoodCostProperty(animal1.offerDetails.get.foodCost)
      val updF8 = defaultAnimalDAO.updatePropertyById(animal1.id, foodCostProp)
      
      val shelterCostProp = AnimalUpdatableProperty.AnimalShelterCostProperty(animal1.offerDetails.get.shelterCost)
      val updF9 = defaultAnimalDAO.updatePropertyById(animal1.id, shelterCostProp)
      
      val vaccinationCostProp = AnimalUpdatableProperty.AnimalVaccinationCostProperty(animal1.offerDetails.get.vaccinationCost)
      val updF10 = defaultAnimalDAO.updatePropertyById(animal1.id, vaccinationCostProp)
      
      val compF = for {
        u1 <- updF1
        u2 <- updF2
        u3 <- updF3
        u4 <- updF4
        u5 <- updF5
        u6 <- updF6
        u7 <- updF7
        u8 <- updF8
        u9 <- updF9
        u10 <- updF10
      } yield(u1 + u2 + u3 + u4 + u5 + u6 + u7 + u8 + u9 + u10)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      findF onSuccess {
        case op: Option[AnimalModel] => {
          op.get mustBe animal1
        }
      }

    }
    
    "update AnimalUpdatableProperty's that are None's" in {
      delay()
      
      animal1 = animal1.copy(
          specificType = None,
          name = None,
          age = None,
          description = None,
          image = None,
          video = None,
          offerDetails = Some(animal1.offerDetails.get.copy(
              foodCost = None,
              shelterCost = None,
              vaccinationCost = None
          ))
      )
      
      val specificTypeProp = AnimalUpdatableProperty.AnimalSpecificTypeProperty(animal1.specificType)
      val updF1 = defaultAnimalDAO.updatePropertyById(animal1.id, specificTypeProp)
      
      val nameProp = AnimalUpdatableProperty.AnimalNameProperty(animal1.name)
      val updF2 = defaultAnimalDAO.updatePropertyById(animal1.id, nameProp)
      
      val ageProp = AnimalUpdatableProperty.AnimalAgeProperty(animal1.age)
      val updF3 = defaultAnimalDAO.updatePropertyById(animal1.id, ageProp)
      
      val descriptionProp = AnimalUpdatableProperty.AnimalDescriptionProperty(animal1.description)
      val updF4 = defaultAnimalDAO.updatePropertyById(animal1.id, descriptionProp)
      
      val imageProp = AnimalUpdatableProperty.AnimalImageProperty(animal1.image)
      val updF5 = defaultAnimalDAO.updatePropertyById(animal1.id, imageProp)
      
      val videoProp = AnimalUpdatableProperty.AnimalVideoProperty(animal1.video)
      val updF6 = defaultAnimalDAO.updatePropertyById(animal1.id, videoProp)
      
      val foodCostProp = AnimalUpdatableProperty.AnimalFoodCostProperty(animal1.offerDetails.get.foodCost)
      val updF7 = defaultAnimalDAO.updatePropertyById(animal1.id, foodCostProp)
      
      val shelterCostProp = AnimalUpdatableProperty.AnimalShelterCostProperty(animal1.offerDetails.get.shelterCost)
      val updF8 = defaultAnimalDAO.updatePropertyById(animal1.id, shelterCostProp)
      
      val vaccinationCostProp = AnimalUpdatableProperty.AnimalVaccinationCostProperty(animal1.offerDetails.get.vaccinationCost)
      val updF9 = defaultAnimalDAO.updatePropertyById(animal1.id, vaccinationCostProp)
      
      val compF = for {
        u1 <- updF1
        u2 <- updF2
        u3 <- updF3
        u4 <- updF4
        u5 <- updF5
        u6 <- updF6
        u7 <- updF7
        u8 <- updF8
        u9 <- updF9
      } yield(u1 + u2 + u3 + u4 + u5 + u6 + u7 + u8 + u9)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      findF onSuccess {
        case op: Option[AnimalModel] => {
          op.get mustBe animal1
        }
      }

    }

    "deny update request when a property has been updated during model manipulation" in {
      delay()
      // find the model and lock it
      val findF = defaultAnimalDAO.lockAndFindById(animal1.id)
      await(findF)
      // change model and issue a property update
      animal1 = animal1.copy(name = Some("updated again name"))
      defaultAnimalDAO.updatePropertyById(animal1.id, AnimalUpdatableProperty.AnimalNameProperty(animal1.name))
      // try to update
      val modelUpdateF = findF.flatMap(
          op => {
            val token = op.get.token
            defaultAnimalDAO.update(UpdatableModelContainer[AnimalModel](animal2, token))
          }
      )
      await(modelUpdateF)
      modelUpdateF onSuccess {
        case res => res.updateStatus mustBe UpdateStatus.Denied
      }

    }
    
  }
  
}