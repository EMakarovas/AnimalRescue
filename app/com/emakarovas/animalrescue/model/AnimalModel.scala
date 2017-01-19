package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.animal.AnimalType

case class AnimalModel
  (id: String,
   name: String,
   animalType: AnimalType)
   extends AbstractModel(id) {
  
}