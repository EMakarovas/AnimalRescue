package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.SpecificType

case class AnimalTypeDetails[T <: Animal](
   animalType: AnimalType[T],
   specificTypeSet: Set[SpecificType[T]]) extends AbstractEntity