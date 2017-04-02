package com.emakarovas.animalrescue.persistence.mongo

import com.emakarovas.animalrescue.model.AbstractPersistableEntity
import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.CollectionCounterEntity
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.UserModel

sealed trait MongoCollectionType[T <: AbstractPersistableEntity]

object MongoCollectionType {
  final case object Animal extends MongoCollectionType[AnimalModel]
  final case object CollectionCounter extends MongoCollectionType[CollectionCounterEntity]
  final case object Offer extends MongoCollectionType[OfferModel]
  final case object Search extends MongoCollectionType[SearchModel]
  final case object User extends MongoCollectionType[UserModel]
}