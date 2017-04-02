package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import java.util.Date
import com.emakarovas.animalrescue.model.enumeration.SearchTerminationReason
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.model.enumeration.Color

case class SearchModel(
   override val id: String,
   override val url: String,
   searchAnimalList: List[SearchAnimalModel],
   location: Location,
   commentList: List[CommentModel],
   startDate: Date,
   endDate: Option[Date],
   isPublic: Boolean,
   userId: String) extends AbstractModel(id) with AbstractURLAccessibleEntity with AbstractPersistableEntity

case class SearchAnimalModel(
   override val id: String,
   animalTypeDetails: AnimalTypeDetails[_ <: Animal],
   gender: Option[Gender],
   colorSet: Set[Color],
   sizeSet: Set[Size],
   tagSet: Set[String],
   castratedOnly: Boolean,
   minAge: Option[Int],
   maxAge: Option[Int],
   potentialAnimalIdList: List[String],
   searchTerminationReason: Option[SearchTerminationReasonModel]) extends AbstractModel(id)

case class SearchTerminationReasonModel
  (searchTerminationReason: SearchTerminationReason,
   text: Option[String]) extends AbstractEntity