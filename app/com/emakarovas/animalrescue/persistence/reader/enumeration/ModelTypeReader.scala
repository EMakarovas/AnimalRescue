package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.enumeration.ModelType

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class ModelTypeReader extends EnumerationReader[ModelType[AbstractModel]] {
  override def read(bson: BSONValue): ModelType[AbstractModel] =
    bson match {
      case BSONString("Animal") => ModelType.Animal
      case BSONString("Comment") => ModelType.Comment
      case BSONString("Image") => ModelType.Image
      case BSONString("Location") => ModelType.Location
      case BSONString("Offer") => ModelType.Offer
      case BSONString("Search") => ModelType.Search
      case BSONString("SearchAnimal") => ModelType.SearchAnimal
      case BSONString("User") => ModelType.User
      case BSONString("Person") => ModelType.Person
      case _ => throw new EnumerationNotFoundException
    }
}