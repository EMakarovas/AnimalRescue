package com.emakarovas.animalrescue.model

import java.util.Date

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason

case class OfferModel
  (override val id: String,
   override val url: String,
   startDate: Date,
   endDate: Option[Date],
   text: String,
   offerAnimalList: List[OfferAnimalModel],
   image: Option[ImageModel],
   video: Option[VideoModel],
   location: LocationModel,
   commentList: List[CommentModel],
   viewedByUserIdList: List[String],
   userId: String)
   extends AbstractModel(id) with AbstractURLAccessibleEntity with AbstractPersistableEntity
   
case class OfferAnimalModel
  (override val id: String,
   override val animalType: AnimalType,
   override val specificType: Option[String],
   override val gender: Gender,
   name: Option[String],
   age: Option[Int], // stored as months
   description: Option[String],
   isCastrated: Boolean,
   image: Option[ImageModel],
   video: Option[VideoModel],
   castrationCost: Option[Double],
   foodCost: Option[Double],
   shelterCost: Option[Double],
   vaccinationCost: Option[Double],
   offerTerminationReason: Option[OfferTerminationReasonModel]) 
     extends AbstractModel(id) with AbstractAnimalEntity

case class OfferTerminationReasonModel
  (offerTerminationReason: OfferTerminationReason,
   text: Option[String]) extends AbstractEntity
