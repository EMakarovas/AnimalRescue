package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.model.CostModel
import com.emakarovas.animalrescue.persistence.dao.CostDAO
import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.writer.CostWriter
import com.emakarovas.animalrescue.persistence.reader.CostReader

import javax.inject._
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.CostModelCollectionType

@Singleton
class DefaultCostDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: CostWriter,
    implicit val reader: CostReader) extends CostDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  val collection = colFactory.getCollection(CostModelCollectionType)
  
}