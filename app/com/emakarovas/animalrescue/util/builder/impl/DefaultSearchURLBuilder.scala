package com.emakarovas.animalrescue.util.builder.impl

import com.emakarovas.animalrescue.util.builder.SearchURLBuilder

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.SearchModel

@Singleton
class DefaultSearchURLBuilder extends SearchURLBuilder {
  override def build(search: SearchModel): String = {
    val id = search.id
    val firstAnimal = search.searchAnimalList(0)
    val animalType = firstAnimal.animalType
    val specificType = firstAnimal.specificType
    s"$id-$animalType-$specificType"
  }
  
}