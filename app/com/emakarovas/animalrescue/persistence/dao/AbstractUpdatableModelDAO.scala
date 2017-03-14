package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity
import com.emakarovas.animalrescue.model.property.DeletableCollectionProperty
import com.emakarovas.animalrescue.model.property.InsertableCollectionProperty
import com.emakarovas.animalrescue.model.property.UpdatableCollectionProperty
import com.emakarovas.animalrescue.model.property.UpdatableProperty
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.dao.update.VersionedModelContainer
import com.emakarovas.animalrescue.persistence.writer.property.PropertyWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.writer.AbstractEntityWriter
import com.emakarovas.animalrescue.model.constants.OfferDetailsConstants
import com.emakarovas.animalrescue.model.OfferDetailsModel
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import com.emakarovas.animalrescue.model.property.unique.UniqueProperty
import reactivemongo.bson.BSONValue

trait AbstractUpdatableModelDAO[T <: AbstractModel with AbstractPersistableEntity] extends AbstractModelDAO[T] {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  // used to write update properties
  implicit protected def propertyWriter: PropertyWriter[T]  
  
  implicit object versionedModelContainerReader extends BSONDocumentReader[VersionedModelContainer[T]] {
    override def read(doc: BSONDocument): VersionedModelContainer[T] = {
      val model = reader.read(doc)
      val version = doc.getAs[Int](MongoConstants.Version).get
      VersionedModelContainer[T](model, version)
    }
  }
  
  implicit object versionedModelContainerWriter extends BSONDocumentWriter[VersionedModelContainer[T]] {
    override def write(container: VersionedModelContainer[T]): BSONDocument = {
      var doc = writer.write(container.model)
      doc = doc ++ BSONDocument(MongoConstants.Version -> container.version)
      doc
    }
  }
  
  override def create(obj: T): Future[Int] = {
    createList += obj
    val container = VersionedModelContainer[T](obj, 1)
    val f = collection.flatMap(_.insert(container))
    f onSuccess {
      case _ => createList -= obj
    }
    f.map(writeRes => writeRes.n)
  }
  
  def findUpdatableById(id: String): Future[Option[VersionedModelContainer[T]]] = {
    val query = BSONDocument(MongoConstants.MongoId -> id)
    collection.flatMap(_.find(query).one)
  }
  
  def findUpdatableByUniqueProperty(property: UniqueProperty[T, Any]): Future[Option[VersionedModelContainer[T]]] = {
    val query = BSONDocument(MongoConstants.Data + "." + property.propertyName -> property.propertyValue)
    collection.flatMap(_.find(query).one)
  }

  def update(container: VersionedModelContainer[T]): Future[Int] = {
    val model = container.model
    val selector = BSONDocument(MongoConstants.MongoId -> model.id,
        MongoConstants.Version -> container.version)
    val update = BSONDocument("$set" -> writer.write(model))
    val versionedUpdate = incrementVersion(update)
    collection.flatMap(_.update(selector, versionedUpdate)).map(writeRes => writeRes.nModified)
  }
  
  def updatePropertyById(id: String, property: UpdatableProperty[T, Any]): Future[Int] = {
    val selector = BSONDocument(MongoConstants.MongoId -> id)
    val update = 
      if(isNone(property.value))
        getUpdateQueryUnset(property.name)
      else getUpdateQuerySet(property.name, property.value)
    val versionedUpdate = incrementVersion(update)
    collection.flatMap(_.update(selector, versionedUpdate)).map(writeRes => writeRes.nModified)
  }
  
  // used when the value is defined (not None)
  protected def getUpdateQuerySet(propName: String, propValue: Any): BSONDocument = {
    BSONDocument(
        "$set" -> BSONDocument(
            MongoConstants.Data + "." + propName -> propValue))
  }
  
  // used when the value is None
  protected def getUpdateQueryUnset(propName: String): BSONDocument = {
    BSONDocument(
        "$unset" -> BSONDocument(
            MongoConstants.Data + "." + propName -> 1))
  }
  
  def updateCollectionPropertyById(id: String, property: UpdatableCollectionProperty[T, Any]): Future[Int] = {
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        MongoConstants.Data + "." + property.collectionName -> BSONDocument(
             "$elemMatch" -> BSONDocument(
                MongoConstants.MongoId -> property.entityId)))
    val update = 
      if(isNone(property.value))
        getUpdateCollectionQueryUnset(property.collectionName, property.propertyName)
      else getUpdateCollectionQuerySet(property.collectionName, property.propertyName, property.value)
    val versionedUpdate = incrementVersion(update)
    collection.flatMap(_.update(selector, versionedUpdate)).map(writeRes => writeRes.nModified)
  }
  
  // used when the value is defined (not None)
  protected def getUpdateCollectionQuerySet(colName: String, propName: String, propValue: Any): BSONDocument = {
    BSONDocument(
        "$set" -> BSONDocument(
            MongoConstants.Data + "." + colName + ".$." + propName -> propValue))
  }
  
  // used when the value is None
  protected def getUpdateCollectionQueryUnset(colName: String, propName: String): BSONDocument = {
    BSONDocument(
        "$unset" -> BSONDocument(
            MongoConstants.Data + "." + colName + ".$." + propName -> "''"))
  }
  
  def insertCollectionPropertyById(id: String, property: InsertableCollectionProperty[T, Any]): Future[Int] = {
    val selector = BSONDocument(
        MongoConstants.MongoId -> id)
    val update = getInsertCollectionQuerySet(property.collectionName, property.value)
    val versionedUpdate = incrementVersion(update)
    collection.flatMap(_.update(selector, versionedUpdate)).map(writeRes => writeRes.nModified)
  }
  
  protected def getInsertCollectionQuerySet(colName: String, propValue: Any): BSONDocument = {
    BSONDocument(
        "$push" -> BSONDocument(
            MongoConstants.Data + "." + colName -> propValue))
  }
  
  def deleteCollectionPropertyById(id: String, property: DeletableCollectionProperty[T, Any]): Future[Int] = {
    val selector = BSONDocument(
        MongoConstants.MongoId -> id)
    val update = getDeleteCollectionPropertyQuery(property.entityId, property.collectionName)
    val versionedUpdate = incrementVersion(update)
    collection.flatMap(_.update(selector, versionedUpdate)).map(writeRes => writeRes.nModified)
  }
  
  protected def getDeleteCollectionPropertyQuery(id: String, colName: String): BSONDocument = {
    BSONDocument(
        "$pull" -> BSONDocument(
            MongoConstants.Data + "." + colName -> BSONDocument(
                MongoConstants.MongoId -> id)))        
  }

  protected def isNone(value: Any): Boolean = value.isInstanceOf[Option[_]] && value.asInstanceOf[Option[_]].isEmpty
  
  protected def incrementVersion(update: BSONDocument): BSONDocument = {
    update ++ BSONDocument(
        "$inc" -> BSONDocument(
            MongoConstants.Version -> 1))
  }
  
}