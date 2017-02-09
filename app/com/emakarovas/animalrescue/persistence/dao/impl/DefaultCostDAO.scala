package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.CostDAO
import com.emakarovas.animalrescue.persistence.mongo.CostModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.reader.CostReader
import com.emakarovas.animalrescue.persistence.writer.CostWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCostDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: CostWriter,
    implicit val reader: CostReader) extends CostDAO {
  
 val collection = colFactory.getCollection(CostModelCollectionType)
  
}