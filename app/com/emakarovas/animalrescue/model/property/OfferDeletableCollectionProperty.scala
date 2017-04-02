package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

sealed abstract class OfferDeletableCollectionProperty[T](
    override val idField: Option[String], 
    override val entityIdentifier: T, 
    override val collectionName: String) 
    extends DeletableCollectionProperty[OfferModel, T] with OfferProperty
    
object OfferDeletableCollectionProperty {
  
  case class OfferAnimal(
      override val entityIdentifier: String)
      extends OfferDeletableCollectionProperty[String](
          Some(MongoConstants.MongoId), entityIdentifier, OfferConstants.OfferAnimalList)
      
}