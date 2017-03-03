package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class OfferTerminationReasonReader extends EnumerationReader[OfferTerminationReason] {
  override def read(bson: BSONValue): OfferTerminationReason =
    bson match {
      case BSONString("GaveToUser") => OfferTerminationReason.GaveToUser
      case BSONString("GaveToSomeoneElse") => OfferTerminationReason.GaveToSomeoneElse
      case BSONString("KeptIt") => OfferTerminationReason.KeptIt
      case BSONString("Other") => OfferTerminationReason.Other
      case _ => throw new EnumerationNotFoundException
    }
}