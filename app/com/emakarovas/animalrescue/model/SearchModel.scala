package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import java.util.Date
import com.emakarovas.animalrescue.model.enumeration.SearchTerminationReason

case class SearchModel(
   override val id: String,
   override val url: String,
   searchAnimalList: List[SearchAnimalModel],
   location: LocationModel,
   commentList: List[CommentModel],
   startDate: Date,
   endDate: Option[Date],
   isPublic: Boolean,
   userId: String) extends AbstractModel(id) with AbstracturlAccessibleEntity with AbstractPersistableEntity

case class SearchAnimalModel(
   override val id: String,
   override val animalType: AnimalType,
   override val specificType: Option[String],
   override val gender: Gender,
   minAge: Option[Int],
   maxAge: Option[Int],
   potentialAnimalIdList: List[String],
   searchTerminationReason: Option[SearchTerminationReasonModel]) extends AbstractModel(id) with AbstractAnimalEntity

case class SearchTerminationReasonModel
  (searchTerminationReason: SearchTerminationReason,
   text: Option[String]) extends AbstractEntity