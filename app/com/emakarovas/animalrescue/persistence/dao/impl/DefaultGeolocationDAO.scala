package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.model.GeolocationModel
import com.emakarovas.animalrescue.persistence.dao.GeolocationDAO
import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.writer.GeolocationWriter
import com.emakarovas.animalrescue.persistence.reader.GeolocationReader

import javax.inject._
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.GeolocationModelCollectionType

@Singleton
class DefaultGeolocationDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: GeolocationWriter,
    implicit val reader: GeolocationReader) extends GeolocationDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  val collection = colFactory.getCollection(GeolocationModelCollectionType)
  
}