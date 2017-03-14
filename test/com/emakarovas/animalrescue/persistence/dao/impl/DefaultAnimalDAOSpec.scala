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
import com.emakarovas.animalrescue.model.property.AnimalUpdatableProperty
import com.emakarovas.animalrescue.persistence.dao.update.VersionedModelContainer
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec
import com.emakarovas.animalrescue.testutil.TestUtils

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultAnimalDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  private val CommonOfferId = "offer id 1"
  private val CommonOwnerId = Some("owner id 1")
  private var animal1 = AnimalModel("id 1", AnimalType.Bird, Some("Parrot"), Gender.Male, Some("animal name 1"), Some(12),
      Some("description 1"), true, Some(ImageModel("img id 1", "img url 1")), Some(VideoModel("vid id 1", "vid url 1")),
      Some(AdoptionDetailsModel(None, TestUtils.buildDate(1, 1, 2011))),
      Some(OfferDetailsModel(Some(18), Some(1), Some(2), Some(3), CommonOfferId, 
          Some(OfferTerminationReasonModel(OfferTerminationReason.KeptIt, Some("term text 1"))))))
  private var animal2 = AnimalModel("id 2", AnimalType.Cat, None, Gender.Unspecified, None, None,
      None, true, None, None,
      Some(AdoptionDetailsModel(CommonOwnerId, TestUtils.buildDate(2, 2, 2012))),
      None)
  private var animal3 = AnimalModel("id 3", AnimalType.Dog, Some("Rottweiler"), Gender.Female, None, Some(24),
      None, false, None, None,
      None,
      Some(OfferDetailsModel(Some(255), Some(4), Some(5), Some(6), CommonOfferId,
          Some(OfferTerminationReasonModel(OfferTerminationReason.GaveToSomeoneElse, Some("term text 3"))))))
  private var animal4 = AnimalModel("id 4", AnimalType.Pig, Some("Cute pig"), Gender.Unspecified, Some("Junior"), None,
      None, false, None, None,
      Some(AdoptionDetailsModel(CommonOwnerId, TestUtils.buildDate(4, 4, 2014))),
      None)
  
  lazy val defaultAnimalDAO = app.injector.instanceOf[DefaultAnimalDAO]
    
  import scala.concurrent.ExecutionContext.Implicits.global

  "DefaultAnimalDAOSpec" should {
    
    "create a new AnimalModel by calling create()" in {
      delay()
      val crF = defaultAnimalDAO.create(animal1)
      await(crF)
      crF onSuccess {
        case n => n mustBe 1
      }
      val findF = crF.flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe animal1
      }
    }
    
    "create a new AnimalModel by calling create(), even with some Nones" in {
      val crF = defaultAnimalDAO.create(animal2)
      await(crF)
      crF onSuccess {
        case n => n mustBe 1
      }
      val findF = crF.flatMap(_ => defaultAnimalDAO.findById(animal2.id))
      await(findF)
      findF onSuccess {
        case animalOpt => animalOpt.get mustBe animal2
      }
    }
    
    "after calling create(), return the AnimalModel in findById even before the save is done in the DB" in {
      val crF = defaultAnimalDAO.create(animal3)
      val findF = defaultAnimalDAO.findById(animal3.id)
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
      val findF = defaultAnimalDAO.findUpdatableById(animal1.id)
      await(findF)
      val updF = findF.flatMap(updateOpt => {
        updateOpt.isDefined mustBe true
        val animal = updateOpt.get.model
        animal mustBe animal1
        animal1 = animal1.copy(description=Some("updated description 1"))
        defaultAnimalDAO.update(VersionedModelContainer[AnimalModel](animal1, updateOpt.get.version))
      })
      await(updF)
      val updatedFindF = updF.flatMap(n => {
        n mustBe 1
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
          isCastrated = false,
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
      
      val castratedProp = AnimalUpdatableProperty.AnimalIsCastratedProperty(animal1.isCastrated)
      val updF6 = defaultAnimalDAO.updatePropertyById(animal1.id, castratedProp)
      
      val imageProp = AnimalUpdatableProperty.AnimalImageProperty(animal1.image)
      val updF7 = defaultAnimalDAO.updatePropertyById(animal1.id, imageProp)
      
      val videoProp = AnimalUpdatableProperty.AnimalVideoProperty(animal1.video)
      val updF8 = defaultAnimalDAO.updatePropertyById(animal1.id, videoProp)
      
      val castrCostProp = AnimalUpdatableProperty.AnimalCastrationCostProperty(animal1.offerDetails.get.castrationCost)
      val updF9 = defaultAnimalDAO.updatePropertyById(animal1.id, castrCostProp)
      
      val foodCostProp = AnimalUpdatableProperty.AnimalFoodCostProperty(animal1.offerDetails.get.foodCost)
      val updF10 = defaultAnimalDAO.updatePropertyById(animal1.id, foodCostProp)
      
      val shelterCostProp = AnimalUpdatableProperty.AnimalShelterCostProperty(animal1.offerDetails.get.shelterCost)
      val updF11 = defaultAnimalDAO.updatePropertyById(animal1.id, shelterCostProp)
      
      val vaccinationCostProp = AnimalUpdatableProperty.AnimalVaccinationCostProperty(animal1.offerDetails.get.vaccinationCost)
      val updF12 = defaultAnimalDAO.updatePropertyById(animal1.id, vaccinationCostProp)
      
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
        u11 <- updF11
        u12 <- updF12
      } yield(u1 + u2 + u3 + u4 + u5 + u6 + u7 + u8 + u9 + u10 + u11 + u12)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      await(findF)
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
              castrationCost = None,
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
      
      val castrCostProp = AnimalUpdatableProperty.AnimalCastrationCostProperty(animal1.offerDetails.get.castrationCost)
      val updF7 = defaultAnimalDAO.updatePropertyById(animal1.id, castrCostProp)
      
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
      await(findF)
      findF onSuccess {
        case op: Option[AnimalModel] => {
          op.get mustBe animal1
        }
      }

    }

    "deny update request when a property has been updated during model manipulation" in {
      delay()
      // find the model and lock it
      val findF = defaultAnimalDAO.findUpdatableById(animal1.id)
      await(findF)
      // change model and issue a property update
      animal1 = animal1.copy(name = Some("updated again name"))
      defaultAnimalDAO.updatePropertyById(animal1.id, AnimalUpdatableProperty.AnimalNameProperty(animal1.name))
      // try to update
      val modelUpdateF = findF.flatMap(
          op => {
            val token = op.get.version
            defaultAnimalDAO.update(VersionedModelContainer[AnimalModel](animal2, token))
          }
      )
      await(modelUpdateF)
      modelUpdateF onSuccess {
        case n => n mustBe 0
      }

    }
    
    "find a list of all AnimalModels when findAll() is called" in {
      delay()
      val saveF = defaultAnimalDAO.create(animal4)
      await(saveF)
      val findF = saveF.flatMap(_ => defaultAnimalDAO.findAll())
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 4
          list.contains(animal1) mustBe true
          list.contains(animal2) mustBe true
          list.contains(animal3) mustBe true
          list.contains(animal4) mustBe true
        }
      }
    }
    
    "find AnimalModels when findByOfferId() is called" in {
      delay()
      val findF = defaultAnimalDAO.findByOfferId(CommonOfferId)
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 2
          list.contains(animal1) mustBe true
          list.contains(animal3) mustBe true
        }
      }
    }
    
    "find AnimalModels when findByOwnerId() is called" in {
      delay()
      val findF = defaultAnimalDAO.findByOwnerId(CommonOwnerId.get)
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 2
          list.contains(animal2) mustBe true
          list.contains(animal4) mustBe true
        }
      }
    }
    
    "delete AnimalModels when deleteById() is called" in {
      delay()
      val singleDeleteF = defaultAnimalDAO.deleteById(animal1.id)
      await(singleDeleteF)
      val findF = singleDeleteF.flatMap(_ => defaultAnimalDAO.findAll())
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 3
          list.contains(animal2) mustBe true
          list.contains(animal3) mustBe true
          list.contains(animal4) mustBe true
        }
      }
      val delF2 = findF.flatMap(_ => defaultAnimalDAO.deleteById(animal2.id))
      val delF3 = findF.flatMap(_ => defaultAnimalDAO.deleteById(animal3.id))
      val delF4 = findF.flatMap(_ => defaultAnimalDAO.deleteById(animal4.id))
      val allDelF = for {
        f2 <- delF2
        f3 <- delF3
        f4 <- delF4
      } yield(f2 + f3 + f4)
      await(allDelF)
      val findF2 = allDelF.flatMap(_ => defaultAnimalDAO.findAll())
      await(findF2)
      findF2 onSuccess {
        case list => list.size mustBe 0
      }
    }
    
  }
  
}