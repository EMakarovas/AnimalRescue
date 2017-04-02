package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason

import javax.inject.Singleton
import reactivemongo.bson.BSONString

@Singleton
class OfferTerminationReasonWriter extends EnumerationWriter[OfferTerminationReason] {
  override def write(gender: OfferTerminationReason) = BSONString(gender.toString)
}