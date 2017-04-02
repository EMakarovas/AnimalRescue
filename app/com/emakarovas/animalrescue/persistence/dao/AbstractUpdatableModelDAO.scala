package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity
import com.emakarovas.animalrescue.model.property.DeletableCollectionProperty
import com.emakarovas.animalrescue.model.property.DeletableNestedCollectionProperty
import com.emakarovas.animalrescue.model.property.InsertableCollectionProperty
import com.emakarovas.animalrescue.model.property.InsertableNestedCollectionProperty
import com.emakarovas.animalrescue.model.property.UpdatableCollectionProperty
import com.emakarovas.animalrescue.model.property.UpdatableProperty
import com.emakarovas.animalrescue.model.property.unique.UniqueProperty
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.dao.update.VersionedModelContainer
import com.emakarovas.animalrescue.persistence.writer.property.PropertyWriter

import reactivemongo.bson.BSONArray
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter

trait AbstractUpdatableModelDAO[T <: AbstractModel with AbstractPersistableEntity] extends AbstractModelDAO[T] {
  
  private val VersionIncrementValue = 1
  
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
      doc = doc ++ (MongoConstants.Version -> container.version)
      doc
    }
  }
  
  override def create(obj: T): Future[Int] = {
    val container = VersionedModelContainer[T](obj, 1)
    collection.flatMap(_.insert(container)).map(writeRes => writeRes.n)
  }
  
  def findUpdatableById(id: String): Future[Option[VersionedModelContainer[T]]] = {
    val query = BSONDocument(MongoConstants.MongoId -> id)
    collection.flatMap(_.find(query).one)
  }
  
  def findUpdatableByUniqueProperty(property: UniqueProperty[T, Any]): Future[Option[VersionedModelContainer[T]]] = {
    val query = BSONDocument(property.propertyName -> property.propertyValue)
    collection.flatMap(_.find(query).one)
  }

  def update(container: VersionedModelContainer[T]): Future[Int] = {
    val model = container.model
    val version = container.version
    val selector = BSONDocument(MongoConstants.MongoId -> model.id,
        MongoConstants.Version -> container.version)
    val newContainer = VersionedModelContainer[T](model, (version+VersionIncrementValue)) 
    collection.flatMap(_.update(selector, newContainer)).map(writeRes => writeRes.nModified)
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
            propName -> propValue))
  }
  
  // used when the value is None
  protected def getUpdateQueryUnset(propName: String): BSONDocument = {
    BSONDocument(
        "$unset" -> BSONDocument(
            propName -> 1))
  }
  
  def updateCollectionPropertyById(id: String, property: UpdatableCollectionProperty[T, Any]): Future[Int] = {
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        property.collectionName -> BSONDocument(
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
            colName + ".$." + propName -> propValue))
  }
  
  // used when the value is None
  protected def getUpdateCollectionQueryUnset(colName: String, propName: String): BSONDocument = {
    BSONDocument(
        "$unset" -> BSONDocument(
            colName + ".$." + propName -> "''"))
  }
  
  def insertCollectionPropertyById(id: String, property: InsertableCollectionProperty[T, Any]): Future[Int] = {
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        property.collectionName -> BSONDocument(
            "$nin" -> BSONArray(property.value)))
    val update = getInsertCollectionQuerySet(property.collectionName, property.value)
    val versionedUpdate = incrementVersion(update)
    collection.flatMap(_.update(selector, versionedUpdate)).map(writeRes => writeRes.nModified)
  }
  
  protected def getInsertCollectionQuerySet(colName: String, propValue: Any): BSONDocument = {
    BSONDocument(
        "$addToSet" -> BSONDocument(
            colName -> propValue))
  }
  
  def insertNestedCollectionPropertyById(id: String, property: InsertableNestedCollectionProperty[T, Any]): Future[Int] = {
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        property.collectionName + "." + property.nestedCollectionName -> BSONDocument(
            "$nin" -> BSONArray(property.value)),
        property.collectionName -> BSONDocument(
            "$elemMatch" -> BSONDocument(
                MongoConstants.MongoId -> property.entityId)))
    val update = getInsertNestedCollectionQuerySet(property.collectionName, property.nestedCollectionName, property.value)
    val versionedUpdate = incrementVersion(update)
    collection.flatMap(_.update(selector, versionedUpdate)).map(writeRes => writeRes.nModified)
  }
  
  protected def getInsertNestedCollectionQuerySet(colName: String, nestedColName: String, value: Any): BSONDocument = {
    BSONDocument(
        "$addToSet" -> BSONDocument(
            colName + ".$." + nestedColName -> value)) 
  }
  
  def deleteCollectionPropertyById(id: String, property: DeletableCollectionProperty[T, Any]): Future[Int] = {
    val idSelector = BSONDocument(
        MongoConstants.MongoId -> id)
    val selector = 
      if(property.idField.isDefined)
        idSelector ++ 
        (property.collectionName + "." + property.idField.get -> property.entityIdentifier)
      else
        idSelector ++
        (property.collectionName -> property.entityIdentifier)  
    val update = getDeleteCollectionPropertyQuery(property.idField, property.entityIdentifier, property.collectionName)
    val versionedUpdate = incrementVersion(update)
    collection.flatMap(_.update(selector, versionedUpdate)).map(writeRes => writeRes.nModified)
  }
  
  protected def getDeleteCollectionPropertyQuery(idField: Option[String], identifier: Any, colName: String): BSONDocument = {
    if(idField.isDefined)
      BSONDocument(
        "$pull" -> BSONDocument(
            colName -> BSONDocument(
                idField.get -> identifier)))      
    else
      BSONDocument(
          "$pull" -> BSONDocument(
              colName -> identifier))
  }
  
  def deleteNestedCollectionPropertyById(id: String, property: DeletableNestedCollectionProperty[T, Any]): Future[Int] = {
    val idSelector = BSONDocument(
        MongoConstants.MongoId -> id,
        property.collectionName -> BSONDocument(
            "$elemMatch" -> BSONDocument(
                MongoConstants.MongoId -> property.entityId)))
    val selector = 
      if(property.nestedCollectionIdField.isDefined)
        idSelector ++
        (property.collectionName + "." + property.nestedCollectionName +
            "." + property.nestedCollectionIdField.get -> property.nestedEntityIdentifier)
      else
        idSelector ++
        (property.collectionName + "." + property.nestedCollectionName -> property.nestedEntityIdentifier)
    val update = getDeleteNestedCollectionPropertyQuery(
        property.collectionName, property.nestedCollectionName, property.nestedCollectionIdField, property.nestedEntityIdentifier)
    val versionedUpdate = incrementVersion(update)
    collection.flatMap(_.update(selector, versionedUpdate)).map(writeRes => writeRes.nModified)
  }
  
  protected def getDeleteNestedCollectionPropertyQuery(
      colName: String, nestedColName: String, nestedColIdField: Option[String], value: Any): BSONDocument = {   
    if(nestedColIdField.isDefined)
      BSONDocument(
        "$pull" -> BSONDocument(
            colName + ".$." + nestedColName -> BSONDocument(
                nestedColIdField.get -> value)))      
    else
      BSONDocument(
          "$pull" -> BSONDocument(
              colName + ".$." + nestedColName -> value))
  }

  protected def isNone(value: Any): Boolean = value.isInstanceOf[Option[_]] && value.asInstanceOf[Option[_]].isEmpty
  
  protected def incrementVersion(update: BSONDocument): BSONDocument = {
    update ++ BSONDocument(
        "$inc" -> BSONDocument(
            MongoConstants.Version -> VersionIncrementValue))
  }
  
}