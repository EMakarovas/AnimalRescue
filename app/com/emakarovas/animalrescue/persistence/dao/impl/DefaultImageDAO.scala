package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.ImageDAO
import com.emakarovas.animalrescue.persistence.mongo.ImageModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.reader.ImageReader
import com.emakarovas.animalrescue.persistence.writer.ImageWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultImageDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: ImageWriter,
    implicit val reader: ImageReader) extends ImageDAO {
  
 val collection = colFactory.getCollection(ImageModelCollectionType)
  
}