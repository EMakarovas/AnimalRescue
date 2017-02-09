package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.GeolocationDAO
import com.emakarovas.animalrescue.persistence.mongo.GeolocationModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.reader.GeolocationReader
import com.emakarovas.animalrescue.persistence.writer.GeolocationWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultGeolocationDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: GeolocationWriter,
    implicit val reader: GeolocationReader) extends GeolocationDAO {
  
 val collection = colFactory.getCollection(GeolocationModelCollectionType)
  
}