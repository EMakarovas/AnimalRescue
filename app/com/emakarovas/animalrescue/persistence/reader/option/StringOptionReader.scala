package com.emakarovas.animalrescue.persistence.reader.option

import javax.inject.Singleton
import reactivemongo.bson.BSONValue

@Singleton
class StringOptionReader extends OptionReader[String] {
  
  override def read(value: BSONValue): Option[String] = {
    value.asOpt[String].orElse(None)
  }
}
