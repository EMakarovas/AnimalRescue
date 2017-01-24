package com.emakarovas.animalrescue.persistence.reader.option

import reactivemongo.bson.BSONNull
import reactivemongo.bson.BSONValue
import reactivemongo.bson.BSONInteger
import com.emakarovas.animalrescue.persistence.writer.option.OptionWriter
import javax.inject.Singleton

@Singleton
class IntOptionReader extends OptionReader[Int] {
  override def read(value: BSONValue): Option[Int] = {
    return Some(1)
  }
}