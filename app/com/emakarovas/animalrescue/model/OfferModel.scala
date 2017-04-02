package com.emakarovas.animalrescue.model

import java.util.Date

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.model.enumeration.Color

case class OfferModel
  (override val id: String,
   override val url: String,
   startDate: Date,
   endDate: Option[Date],
   text: String,
   offerAnimalList: List[OfferAnimalModel],
   image: Option[ImageModel],
   video: Option[VideoModel],
   location: Location,
   commentList: List[CommentModel],
   viewedByUserIdSet: Set[String],
   userId: String)
   extends AbstractModel(id) with AbstractURLAccessibleEntity with AbstractPersistableEntity
   
case class OfferAnimalModel
  (override val id: String,
   animalTypeDetails: AnimalTypeDetails[_ <: Animal],
   gender: Option[Gender],
   name: Option[String],
   age: Int, // stored as months
   description: Option[String],
   colorSet: Set[Color],
   size: Size,
   tagSet: Set[String],
   isCastrated: Boolean,
   image: Option[ImageModel],
   video: Option[VideoModel],
   castrationCost: Option[Double],
   foodCost: Option[Double],
   shelterCost: Option[Double],
   vaccinationCost: Option[Double],
   offerTerminationReason: Option[OfferTerminationReasonModel]) 
     extends AbstractModel(id)

case class OfferTerminationReasonModel
  (offerTerminationReason: OfferTerminationReason,
   text: Option[String]) extends AbstractEntity
