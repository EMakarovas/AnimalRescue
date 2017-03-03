package com.emakarovas.animalrescue.persistence.writer.enumeration

import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONWriter

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.enumeration.SearchTerminationReason

@Singleton
class SearchTerminationReasonWriter extends EnumerationWriter[SearchTerminationReason] {
  override def write(gender: SearchTerminationReason) = BSONString(gender.toString)
}