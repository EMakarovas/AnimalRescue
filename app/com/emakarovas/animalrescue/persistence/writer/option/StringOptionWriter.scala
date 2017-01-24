package com.emakarovas.animalrescue.persistence.writer.option

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue
import reactivemongo.bson.BSONNull

@Singleton
class StringOptionWriter extends OptionWriter[String] {
  def write(option: Option[String]): BSONValue = {
    if(option.isDefined) return new BSONString(option.get)
    else return BSONNull
  }
}