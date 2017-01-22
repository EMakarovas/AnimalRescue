package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.AnimalDAO
import com.emakarovas.animalrescue.persistence.mongo.AnimalModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.reader.AnimalReader
import com.emakarovas.animalrescue.persistence.writer.AnimalWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAnimalDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: AnimalWriter,
    implicit val reader: AnimalReader) extends AnimalDAO {
  
 val collection = colFactory.getCollection(AnimalModelCollectionType)
  
}