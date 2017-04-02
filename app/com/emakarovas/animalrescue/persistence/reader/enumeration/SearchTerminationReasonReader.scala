package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.SearchTerminationReason

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class SearchTerminationReasonReader extends EnumerationReader[SearchTerminationReason] {
  override def read(bson: BSONValue): SearchTerminationReason =
    bson match {
      case BSONString(str) => SearchTerminationReason.valueOf(str)
      case _ => throw new IllegalBSONValueException()
    }
}