package com.emakarovas.animalrescue.persistence.reader.option

import javax.inject.Singleton
import reactivemongo.bson.BSONValue

@Singleton
class IntOptionReader extends OptionReader[Int] {
  
  override def read(value: BSONValue): Option[Int] = {
    value.asOpt[Int].orElse(None)
  }
}
