package com.emakarovas.animalrescue.model.animal

import com.emakarovas.animalrescue.model.common.ModelEnumeration

object AnimalType extends ModelEnumeration {
  type AnimalType = Value
  val Dog, 
  Cat, 
  Rat, 
  Pig, 
  GuineaPig, 
  Ferret, 
  Rabbit, 
  Mouse, 
  Fish, 
  Bird, 
  Hedgehog, 
  Hamster, 
  Chinchilla, 
  Snake = Value
}