package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity
import com.emakarovas.animalrescue.model.property.InsertableCollectionProperty
import com.emakarovas.animalrescue.model.property.UpdatableCollectionProperty
import com.emakarovas.animalrescue.model.property.UpdatableProperty
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.dao.update.UpdatableModelContainer
import com.emakarovas.animalrescue.persistence.dao.update.UpdateResult
import com.emakarovas.animalrescue.persistence.dao.update.UpdateStatus
import com.emakarovas.animalrescue.persistence.writer.property.PropertyWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import reactivemongo.bson.BSONDocument

trait AbstractUpdatableModelDAO[T <: AbstractModel with AbstractPersistableEntity] extends AbstractModelDAO[T] {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  protected def stringGenerator: StringGenerator
  // used to write update properties
  implicit protected def propertyWriter: PropertyWriter[T]
  // key is ID, value is token
  protected var activeUpdateTokenMap = scala.collection.mutable.Map[String, String]()
  
  
  def lockAndFindById(id: String): Future[Option[UpdatableModelContainer[T]]] = {
    val token = stringGenerator.generate(20);
    activeUpdateTokenMap += (id -> token)
    val query = BSONDocument(MongoConstants.MongoId -> id)
    val f = collection.flatMap(_.find(query).one)
    f.map((op) => {
      op match {
        case Some(model) => Some(UpdatableModelContainer[T](model, token))
        case None => {
          clearActiveUpdateToken(id)
          None
        }
      }
    })
  }

  def update(container: UpdatableModelContainer[T]): Future[UpdateResult] = {
    val activeUpdateToken = activeUpdateTokenMap.get(container.model.id)
    if(activeUpdateToken.isEmpty || activeUpdateToken.get!=container.token)
      return Future { UpdateResult(UpdateStatus.Denied, 0) }
    val model = container.model
    val selector = BSONDocument("_id" -> model.id)
    collection.flatMap(_.update(selector, model)).map(writeRes => UpdateResult(UpdateStatus.Executed, writeRes.n))
  }
  
  def updatePropertyById(id: String, property: UpdatableProperty[T, Any]): Future[Int] = {
    clearActiveUpdateToken(id)
    val selector = BSONDocument("_id" -> id)
    val update = 
      if(isNone(property.value))
        getUpdateQueryUnset(property.name)
      else getUpdateQuerySet(property.name, property.value)
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.n)
  }
  
  // used when the value is defined (not None)
  protected def getUpdateQuerySet(propName: String, propValue: Any): BSONDocument = {
    BSONDocument(
        "$set" -> BSONDocument(
            propName -> propValue))
  }
  
  // used when the value is None
  protected def getUpdateQueryUnset(propName: String): BSONDocument = {
    BSONDocument(
        "$unset" -> BSONDocument(
            propName -> 1))
  }
  
  def updateCollectionPropertyById(id: String, property: UpdatableCollectionProperty[T, Any]): Future[Int] = {
    clearActiveUpdateToken(id)
    val selector = BSONDocument(
        "_id" -> id,
        property.collectionName -> BSONDocument(
             "$elemMatch" -> BSONDocument(
                "_id" -> property.entityId)))
    val update = 
      if(isNone(property.value))
        getUpdateCollectionQueryUnset(property.collectionName, property.propertyName)
      else getUpdateCollectionQuerySet(property.collectionName, property.propertyName, property.value)
        
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.n)
  }
  
  // used when the value is defined (not None)
  protected def getUpdateCollectionQuerySet(colName: String, propName: String, propValue: Any): BSONDocument = {
    BSONDocument(
        "$set" -> BSONDocument(
            colName + ".$." + propName -> propValue))
  }
  
  // used when the value is None
  protected def getUpdateCollectionQueryUnset(colName: String, propName: String): BSONDocument = {
    BSONDocument(
        "$unset" -> BSONDocument(
            colName + ".$." + propName -> "''"))
  }
  
  def insertCollectionPropertyById(id: String, property: InsertableCollectionProperty[T, Any]): Future[Int] = {
    clearActiveUpdateToken(id)
    val selector = BSONDocument(
        "_id" -> id)
    val update = getInsertCollectionQuerySet(property.collectionName, property.value)
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.n)
  }
  
  protected def getInsertCollectionQuerySet(colName: String, propValue: Any): BSONDocument = {
    BSONDocument(
        "$push" -> BSONDocument(
            colName -> propValue))
  }
  
  protected def clearActiveUpdateToken(modelId: String) = activeUpdateTokenMap -= modelId
  
  protected def isNone(value: Any): Boolean = value.isInstanceOf[Option[_]] && value.asInstanceOf[Option[_]].isEmpty
  
}