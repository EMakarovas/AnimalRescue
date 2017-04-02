package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.constants.AnimalConstants

sealed abstract class AnimalInsertableCollectionProperty[T](
    override val collectionName: String, override val value: T) extends InsertableCollectionProperty[AnimalModel, T] with AnimalProperty

object AnimalInsertableCollectionProperty {
  
  case class Color(override val value: com.emakarovas.animalrescue.model.enumeration.Color)
    extends AnimalInsertableCollectionProperty[com.emakarovas.animalrescue.model.enumeration.Color](AnimalConstants.ColorSet, value)
  
  case class Tag(override val value: String)
    extends AnimalInsertableCollectionProperty[String](AnimalConstants.TagSet, value)
    
}