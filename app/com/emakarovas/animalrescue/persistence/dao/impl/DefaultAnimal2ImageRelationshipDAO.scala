package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.Animal2ImageRelationshipDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.Animal2ImageRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.reader.Animal2ImageRelationshipReader
import com.emakarovas.animalrescue.persistence.writer.Animal2ImageRelationshipWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAnimal2ImageRelationshipDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: Animal2ImageRelationshipWriter,
    implicit val reader: Animal2ImageRelationshipReader) extends Animal2ImageRelationshipDAO {
  
 val collection = colFactory.getCollection(Animal2ImageRelationshipCollectionType)

}