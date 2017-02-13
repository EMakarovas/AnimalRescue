package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import java.util.Date

case class WishModel(
    override val id: String,
    animalType: AnimalType,
    specificType: Option[String],
    gender: Gender,
    minAge: Option[Int],
    maxAge: Option[Int],
    geolocation: GeolocationModel,
    startDate: Date,
    endDate: Option[Date],
    userId: String) extends AbstractModel(id)