package com.emakarovas.animalrescue.persistence.writer.option

import javax.inject.Singleton
import reactivemongo.bson.BSONValue
import reactivemongo.bson.BSONNull
import reactivemongo.bson.BSONInteger

@Singleton
class IntOptionWriter extends OptionWriter[Int] {
  def write(option: Option[Int]): BSONValue = {
    if(option.isDefined) return new BSONInteger(option.get)
    else return BSONNull
  }

}