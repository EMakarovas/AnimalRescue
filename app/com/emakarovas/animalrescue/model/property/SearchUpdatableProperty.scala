package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.SearchConstants

sealed abstract class SearchUpdatableProperty[T](
    override val name: String, override val value: T) extends UpdatableProperty[SearchModel, T] with SearchProperty
    
object SearchUpdatableProperty {
  case class SearchIsPublicProperty(override val value: Boolean) 
    extends SearchUpdatableProperty[Boolean](SearchConstants.IsPublic, value)
}