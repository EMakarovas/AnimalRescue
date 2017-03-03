package com.emakarovas.animalrescue.model.constants

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import java.util.Date
import com.emakarovas.animalrescue.model.enumeration.SearchTerminationReason

object SearchConstants {
  val Id = "id"
  val Url = "url"
  val SearchAnimalList = "searchAnimalList"
  val Location = "location"
  val CommentList = "commentList"
  val StartDate = "startDate"
  val EndDate = "endDate"
  val IsPublic = "isPublic"
  val UserId = "userId"
}

object SearchAnimalConstants {
  val Id = "id"
  val AnimalType = "animalType"
  val SpecificType = "specificType"
  val Gender = "gender"
  val MinAge = "minAge"
  val MaxAge = "maxAge"
  val PotentialAnimalIdList = "potentialAnimalIdList"
  val SearchTerminationReason = "searchTerminationReason"
}

object SearchTerminationReasonConstants {
  val SearchTerminationReason = "searchTerminationReason"
  val Text = "text"
}