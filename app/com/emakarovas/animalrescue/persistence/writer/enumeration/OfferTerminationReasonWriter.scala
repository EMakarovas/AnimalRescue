package com.emakarovas.animalrescue.persistence.writer.enumeration

import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONWriter

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason

@Singleton
class OfferTerminationReasonWriter extends EnumerationWriter[OfferTerminationReason] {
  override def write(gender: OfferTerminationReason) = BSONString(gender.toString)
}