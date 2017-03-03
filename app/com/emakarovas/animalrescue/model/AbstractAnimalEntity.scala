package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender

trait AbstractAnimalEntity extends AbstractEntity {
  def animalType: AnimalType
  def specificType: Option[String]
  def gender: Gender
}