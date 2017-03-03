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
import com.emakarovas.animalrescue.util.generator.StringGenerator

import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONInteger
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue
import reactivemongo.bson.BSONWriter

trait AbstractUpdatableModelDAO[T <: AbstractModel with AbstractPersistableEntity] extends AbstractModelDAO[T] {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  protected def stringGenerator: StringGenerator
  protected var activeUpdateToken: String = ""
  
  /*
   * TODO: these will have to change to a specific list of available
   * constants for every model. E.g. AnimalModel's name could be changed
   */
  implicit val anyWriter = new AnyWriter
  
  def lockAndFind(id: String): Future[Option[UpdatableModelContainer[T]]] = {
    val query = BSONDocument(MongoConstants.MongoId -> id)
    val f = collection.flatMap(_.find(query).one)
    f.map((op) => {
      val token = stringGenerator.generate(20);
      activeUpdateToken = token
      op match {
        case Some(model) => Some(UpdatableModelContainer[T](model, token))
        case None => None
      }
    })
  }

  def update(container: UpdatableModelContainer[T]): Future[UpdateResult] = {
    if(activeUpdateToken!=container.token)
      Future { UpdateResult(UpdateStatus.Denied, 0) }
    val model = container.model
    val selector = BSONDocument("_id" -> model.id)
    collection.flatMap(_.update(selector, model)).map(writeRes => UpdateResult(UpdateStatus.Executed, writeRes.n))
  }
  
  def updatePropertyById(id: String, property: UpdatableProperty[T, Any]): Future[Int] = {
    clearActiveUpdateToken()
    val selector = BSONDocument("_id" -> id)
    val update = BSONDocument(
        "$set" -> BSONDocument(
            property.name -> property.value))
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.n)
  }
  
  def updateCollectionPropertyById(id: String, property: UpdatableCollectionProperty[T, Any]): Future[Int] = {
    clearActiveUpdateToken()
    val selector = BSONDocument(
        "_id" -> id,
        property.collectionName -> BSONDocument(
            "_id" -> property.entityId))
    val update = BSONDocument(
        "$set" -> BSONDocument(
            property.propertyName -> property.value))
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.n)
  }
  
  def insertCollectionPropertyById(id: String, property: InsertableCollectionProperty[T, Any]): Future[Int] = {
    clearActiveUpdateToken()
    val selector = BSONDocument(
        "_id" -> id)
    val update = BSONDocument(
        "$set" -> BSONDocument(
            property.collectionName -> property.value))
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.n)
  }
  
  protected def clearActiveUpdateToken() = activeUpdateToken = ""
  
}

class AnyWriter extends BSONWriter[Any, BSONValue] {
  override def write(any: Any): BSONValue = {
    any match {
    case ob: String => BSONString(ob)
    case ob: Int => BSONInteger(ob)
    case op: Some[_] => write(op.get)
    case _ => BSONString(any.toString())
    }
  }
}