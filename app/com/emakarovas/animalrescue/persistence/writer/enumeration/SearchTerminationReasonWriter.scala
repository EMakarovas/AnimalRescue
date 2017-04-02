package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.enumeration.SearchTerminationReason

import javax.inject.Singleton
import reactivemongo.bson.BSONString

@Singleton
class SearchTerminationReasonWriter extends EnumerationWriter[SearchTerminationReason] {
  override def write(gender: SearchTerminationReason) = BSONString(gender.toString)
}