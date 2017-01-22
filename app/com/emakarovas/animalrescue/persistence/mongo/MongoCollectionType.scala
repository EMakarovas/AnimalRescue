package com.emakarovas.animalrescue.persistence.mongo

sealed trait MongoCollectionType

final case object AnimalModelCollectionType extends MongoCollectionType
final case object PersonModelCollectionType extends MongoCollectionType
final case object UserModelCollectionType extends MongoCollectionType
final case object User2PersonRelationshipCollectionType extends MongoCollectionType
