package com.emakarovas.animalrescue.model

import java.util.Date

import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.Size

/*
 * TODO - not finished
 * This will probably take a while to be finalized
 * Just carrying on with everything else
 */
case class AnimalModel
  (override val id: String,
   override val url: String,
   animalTypeDetails: AnimalTypeDetails[_ <: Animal],
   gender: Option[Gender],
   name: Option[String],
   age: Int, // stored as months
   description: Option[String],
   colorSet: Set[Color],
   size: Size,
   tagSet: Set[String],
   isCastrated: Boolean,
   image: Option[ImageModel],
   video: Option[VideoModel],
   adoptionDetails: Option[AdoptionDetails])
   extends AbstractModel(id) with AbstractPersistableEntity with AbstractURLAccessibleEntity

case class AdoptionDetails
  (ownerId: Option[String], // ID of the user
   date: Date) extends AbstractEntity