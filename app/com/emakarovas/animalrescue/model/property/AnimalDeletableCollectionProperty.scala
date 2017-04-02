package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.constants.AnimalConstants

sealed abstract class AnimalDeletableCollectionProperty[T](
    override val idField: Option[String], 
    override val entityIdentifier: T, 
    override val collectionName: String) 
    extends DeletableCollectionProperty[AnimalModel, T] with AnimalProperty
    
object AnimalDeletableCollectionProperty {
  
  case class Color(override val entityIdentifier: com.emakarovas.animalrescue.model.enumeration.Color)
    extends AnimalDeletableCollectionProperty[com.emakarovas.animalrescue.model.enumeration.Color](
        None, entityIdentifier, AnimalConstants.ColorSet)
        
  case class Tag(override val entityIdentifier: String)
    extends AnimalDeletableCollectionProperty[String](
        None, entityIdentifier, AnimalConstants.TagSet)
  
}