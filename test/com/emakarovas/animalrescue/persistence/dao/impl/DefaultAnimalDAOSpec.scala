package com.emakarovas.animalrescue.persistence.dao.impl

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.AnimalTypeDetails
import com.emakarovas.animalrescue.testutil.TestUtils
import com.emakarovas.animalrescue.persistence.dao.update.VersionedModelContainer
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.property.AnimalUpdatableProperty
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.AdoptionDetails
import com.emakarovas.animalrescue.model.enumeration.Animal

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout
import scala.concurrent.Future
import com.emakarovas.animalrescue.model.property.AnimalInsertableCollectionProperty
import com.emakarovas.animalrescue.model.property.AnimalDeletableCollectionProperty

class DefaultAnimalDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  private val CommonOwnerId = Some("owner id 1")
  private var animal1 = AnimalModel(
      "id 1", 
      "url 1", 
      AnimalTypeDetails[Animal.Dog](
          AnimalType.Dog,
          Set(SpecificType.GoldenRetriever, SpecificType.CockerSpaniel)),
      Some(Gender.Male),
      Some("animal name 1"), 
      12,
      Some("description 1"), 
      Set(Color.White, Color.Black),
      Size.Small,
      Set("tag 1", "tag 2"),
      true, 
      Some(ImageModel("img id 1", "img url 1")), 
      Some(VideoModel("vid id 1", "vid url 1")),
      Some(AdoptionDetails(None, TestUtils.buildDate(1, 1, 2011))))
  private var animal2 = AnimalModel(
      "id 2", 
      "url 2", 
      AnimalTypeDetails[Animal.Bird](
          AnimalType.Bird,
          Set(SpecificType.Parrot)),
      None,
      None, 
      22,
      None, 
      Set(Color.Red, Color.Black),
      Size.Small,
      Set(),
      true, 
      None, 
      None,
      Some(AdoptionDetails(CommonOwnerId, TestUtils.buildDate(2, 2, 2022))))
  private var animal3 = AnimalModel(
      "id 3", 
      "url 3", 
      AnimalTypeDetails[Animal.Dog](
          AnimalType.Dog,
          Set(SpecificType.GoldenRetriever, SpecificType.CockerSpaniel)),
      Some(Gender.Male),
      Some("animal name 3"), 
      32,
      Some("description 3"), 
      Set(Color.White, Color.Black),
      Size.Small,
      Set("tag 3", "tag 2"),
      true, 
      Some(ImageModel("img id 3", "img url 3")), 
      Some(VideoModel("vid id 3", "vid url 3")),
      Some(AdoptionDetails(CommonOwnerId, TestUtils.buildDate(3, 3, 2033))))

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
          gender = Some(Gender.Female),
          name = Some("updated animal name"),
          age = 9001,
          description = Some("updated descr"),
          size = Size.Medium,
          isCastrated = false,
          image = Some(ImageModel("updated id", "updated url")),
          video = Some(VideoModel("updated vid id", "updated vid url"))
      )

      val genderProp = AnimalUpdatableProperty.Gender(animal1.gender)
      val updF1 = defaultAnimalDAO.updatePropertyById(animal1.id, genderProp)
      
      val nameProp = AnimalUpdatableProperty.Name(animal1.name)
      val updF2 = defaultAnimalDAO.updatePropertyById(animal1.id, nameProp)
      
      val ageProp = AnimalUpdatableProperty.Age(animal1.age)
      val updF3 = defaultAnimalDAO.updatePropertyById(animal1.id, ageProp)
      
      val descriptionProp = AnimalUpdatableProperty.Description(animal1.description)
      val updF4 = defaultAnimalDAO.updatePropertyById(animal1.id, descriptionProp)
      
      val sizeProp = AnimalUpdatableProperty.Size(animal1.size)
      val updF5 = defaultAnimalDAO.updatePropertyById(animal1.id, sizeProp)
      
      val castratedProp = AnimalUpdatableProperty.IsCastrated(animal1.isCastrated)
      val updF6 = defaultAnimalDAO.updatePropertyById(animal1.id, castratedProp)
      
      val imageProp = AnimalUpdatableProperty.Image(animal1.image)
      val updF7 = defaultAnimalDAO.updatePropertyById(animal1.id, imageProp)
      
      val videoProp = AnimalUpdatableProperty.Video(animal1.video)
      val updF8 = defaultAnimalDAO.updatePropertyById(animal1.id, videoProp)
      
      val compF = for {
        u1 <- updF1
        u2 <- updF2
        u3 <- updF3
        u4 <- updF4
        u5 <- updF5
        u6 <- updF6
        u7 <- updF7
        u8 <- updF8
      } yield(u1 + u2 + u3 + u4 + u5 + u6 + u7 + u8)
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
          gender = None,
          name = None,
          description = None,
          image = None,
          video = None
      )
      
      val genderProp = AnimalUpdatableProperty.Gender(animal1.gender)
      val updF1 = defaultAnimalDAO.updatePropertyById(animal1.id, genderProp)
      
      val nameProp = AnimalUpdatableProperty.Name(animal1.name)
      val updF2 = defaultAnimalDAO.updatePropertyById(animal1.id, nameProp)
      
      val descriptionProp = AnimalUpdatableProperty.Description(animal1.description)
      val updF3 = defaultAnimalDAO.updatePropertyById(animal1.id, descriptionProp)
      
      val imageProp = AnimalUpdatableProperty.Image(animal1.image)
      val updF4 = defaultAnimalDAO.updatePropertyById(animal1.id, imageProp)
      
      val videoProp = AnimalUpdatableProperty.Video(animal1.video)
      val updF5 = defaultAnimalDAO.updatePropertyById(animal1.id, videoProp)
      
      val compF = for {
        u1 <- updF1
        u2 <- updF2
        u3 <- updF3
        u4 <- updF4
        u5 <- updF5
      } yield(u1 + u2 + u3 + u4 + u5)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      await(findF)
      findF onSuccess {
        case op: Option[AnimalModel] => {
          op.get mustBe animal1
        }
      }

    }
    
    "successfully insert AnimalInsertableCollectionProperty's and do nothing when the properties are duplicated" in {
      delay()
      
      val newColor = Color.Green
      val newTag = "some new tag"
      
      animal1 = animal1.copy(
          colorSet = animal1.colorSet + newColor,
          tagSet = animal1.tagSet + newTag
      )
      
      val coProp = AnimalInsertableCollectionProperty.Color(newColor)
      val f1 = defaultAnimalDAO.insertCollectionPropertyById(animal1.id, coProp)
      f1 onSuccess {
        case n => n mustBe 1
      }
      
      val tProp = AnimalInsertableCollectionProperty.Tag(newTag)
      val f2 = defaultAnimalDAO.insertCollectionPropertyById(animal1.id, tProp)
      f2 onSuccess {
        case n => n mustBe 1
      }
      
      val f = for {
        ft1 <- f1
        ft2 <- f2
      } yield(ft1 + ft2)
      await(f)
      
      val findF = f.flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      await(findF)
      findF onSuccess {
        case opt => opt.get mustBe animal1
      }
      
      val failF1 = findF.flatMap(_ => defaultAnimalDAO.insertCollectionPropertyById(animal1.id, coProp))
      await(failF1)
      failF1 onSuccess {
        case n => n mustBe 0
      }

      val failF2 = findF.flatMap(_ => defaultAnimalDAO.insertCollectionPropertyById(animal1.id, tProp))
      await(failF2)
      failF2 onSuccess {
        case n => n mustBe 0
      }  

    }
    
    "successfully delete AnimalDeletableCollectionProperty's and do nothing when the properties are non-existent" in {
      delay()
      
      val color = Color.Green
      val tag = "some new tag"
      
      animal1 = animal1.copy(
          colorSet = animal1.colorSet - color,
          tagSet = animal1.tagSet - tag
      )
      
      val coProp = AnimalDeletableCollectionProperty.Color(color)
      val f1 = defaultAnimalDAO.deleteCollectionPropertyById(animal1.id, coProp)
      f1 onSuccess {
        case n => n mustBe 1
      }
      
      val tProp = AnimalDeletableCollectionProperty.Tag(tag)
      val f2 = defaultAnimalDAO.deleteCollectionPropertyById(animal1.id, tProp)
      f2 onSuccess {
        case n => n mustBe 1
      }
      
      val f = for {
        ft1 <- f1
        ft2 <- f2
      } yield(ft1 + ft2)
      await(f)
      
      val findF = f.flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      await(findF)
      findF onSuccess {
        case opt => opt.get mustBe animal1
      }
      
      val failF1 = findF.flatMap(_ => defaultAnimalDAO.deleteCollectionPropertyById(animal1.id, coProp))
      await(failF1)
      failF1 onSuccess {
        case n => n mustBe 0
      }
      val failF2 = findF.flatMap(_ => defaultAnimalDAO.deleteCollectionPropertyById(animal1.id, tProp))
      await(failF2)
      failF2 onSuccess {
        case n => n mustBe 0
      }  

    }
    
    "add a specificType when addSpecificTypeById is called" in {
      delay()
      
      val newSpecificType = SpecificType.Poodle
      val oldTypeDetails = animal1.animalTypeDetails
      val newTypeDetails = oldTypeDetails.copy(
          specificTypeSet = oldTypeDetails.specificTypeSet ++ Set(newSpecificType)
      )
      
      animal1 = animal1.copy(animalTypeDetails = newTypeDetails)
      
      val updF = defaultAnimalDAO.addSpecificTypeById(animal1.id, newSpecificType)
      await(updF)
      updF onSuccess {
        case n => n mustBe 1
      }
      val findF = updF.flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      await(findF)
      findF onSuccess {
        case opt => opt.get mustBe animal1
      }
    }
    
    "not update the DB when addSpecificTypeById is called if the specificType already exists, or is not of the same Animal type" in {
      delay()
      
      val updF1 = defaultAnimalDAO.addSpecificTypeById(animal1.id, SpecificType.CockerSpaniel) // already contains
      await(updF1)
      updF1 onSuccess {
        case n => n mustBe 0
      }
      
      val updF2 = defaultAnimalDAO.addSpecificTypeById(animal1.id, SpecificType.Parrot)
      await(updF2)
      updF2 onSuccess {
        case n => n mustBe 0
      }
      
      val f = for {
        u1 <- updF1
        u2 <- updF2
      } yield(u1 + u2)
      
      val findF = f.flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      await(findF)
      findF onSuccess {
        case opt => opt.get mustBe animal1
      }
      
    }
    
    "delete a specificType when deleteSpecificTypeById is called" in {
      delay()
      
      val deleteSpecificType = SpecificType.Poodle
      val oldTypeDetails = animal1.animalTypeDetails
      val newTypeDetails = oldTypeDetails.copy(
          specificTypeSet = Set[SpecificType[Animal.Dog]](SpecificType.CockerSpaniel, SpecificType.GoldenRetriever)
      )
      
      animal1 = animal1.copy(animalTypeDetails = newTypeDetails)
      
      val updF = defaultAnimalDAO.removeSpecificTypeById(animal1.id, deleteSpecificType)
      await(updF)
      updF onSuccess {
        case n => n mustBe 1
      }
      val findF = updF.flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      await(findF)
      findF onSuccess {
        case opt => opt.get mustBe animal1
      }
    }
    
    "not delete anything when deleteSpecificTypeById is called with wrong or non-existing SpecificType" in {
      delay()
      
      val updF = defaultAnimalDAO.removeSpecificTypeById(animal1.id, SpecificType.Poodle)
      await(updF)
      updF onSuccess {
        case n => n mustBe 0
      }
      
      val updF2 = defaultAnimalDAO.removeSpecificTypeById(animal1.id, SpecificType.Parrot)
      await(updF2)
      updF2 onSuccess {
        case n => n mustBe 0
      }
      val findF = Future.sequence(List(updF, updF2)).flatMap(_ => defaultAnimalDAO.findById(animal1.id))
      await(findF)
      findF onSuccess {
        case opt => opt.get mustBe animal1
      }
    }

    "deny update request when a property has been updated during model manipulation" in {
      delay()
      // find the model and lock it
      val findF = defaultAnimalDAO.findUpdatableById(animal1.id)
      await(findF)
      // change model and issue a property update
      animal1 = animal1.copy(name = Some("updated again name"))
      defaultAnimalDAO.updatePropertyById(animal1.id, AnimalUpdatableProperty.Name(animal1.name))
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
    
    "find a list of the provided number of AnimalModels when findAll(count) is called" in {
      
      val crF = defaultAnimalDAO.create(animal3)
      await(crF)

      val findF = crF.flatMap(_ => defaultAnimalDAO.findAll(20))
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 3
          list.contains(animal1) mustBe true
          list.contains(animal2) mustBe true
          list.contains(animal3) mustBe true
        }
      }
      val findF2 = findF.flatMap(_ => defaultAnimalDAO.findAll(2))
      await(findF2)
      findF2 onSuccess {
        case list => {
          list.size mustBe 2
          list.contains(animal1) mustBe true 
          list.contains(animal2) mustBe true
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
          list.contains(animal3) mustBe true
        }
      }
    }
    
    "delete AnimalModels when deleteById() is called" in {
      delay()
      val singleDeleteF = defaultAnimalDAO.deleteById(animal1.id)
      await(singleDeleteF)
      val findF = singleDeleteF.flatMap(_ => defaultAnimalDAO.findAll(20))
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 2
          list.contains(animal2) mustBe true
          list.contains(animal3) mustBe true
        }
      }
      val delF2 = findF.flatMap(_ => defaultAnimalDAO.deleteById(animal2.id))
      val delF3 = findF.flatMap(_ => defaultAnimalDAO.deleteById(animal3.id))
      val allDelF = for {
        f2 <- delF2
        f3 <- delF3
      } yield(f2 + f3)
      await(allDelF)
      val findF2 = allDelF.flatMap(_ => defaultAnimalDAO.findAll(30))
      await(findF2)
      findF2 onSuccess {
        case list => list.size mustBe 0
      }
    }
    
  }
  
}