package com.emakarovas.animalrescue.model

import java.util.Date

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender

/*
 * TODO - not finished
 * This will probably take a while to be finalized
 * Just carrying on with everything else
 */
case class AnimalModel
  (override val id: String,
   override val url: String,
   override val animalType: AnimalType,
   override val specificType: Option[String],
   override val gender: Gender,
   name: Option[String],
   age: Option[Int], // stored as months
   description: Option[String],
   isCastrated: Boolean,
   image: Option[ImageModel],
   video: Option[VideoModel],
   adoptionDetails: Option[AdoptionDetailsModel])
   extends AbstractModel(id) with AbstractAnimalEntity with AbstractPersistableEntity with AbstractURLAccessibleEntity

case class AdoptionDetailsModel
  (ownerId: Option[String], // ID of the user
   date: Date) extends AbstractEntity
