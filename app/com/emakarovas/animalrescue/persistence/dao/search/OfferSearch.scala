package com.emakarovas.animalrescue.persistence.dao.search

import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.model.enumeration.SpecificType

/**
 * The class used to search for offers
 */
case class OfferSearch[T <: Animal](
    animalType: Option[AnimalType[T]],
    specificTypeSet: Set[SpecificType[T]],
    gender: Option[Gender],
    colorSet: Set[Color],
    sizeSet: Set[Size],
    tagSet: Set[String],
    countrySet: Set[String],
    citySet: Set[String],
    ageBoundaries: OfferSearchAgeBoundaries,
    castratedOnly: Boolean)
    
case class OfferSearchAgeBoundaries(val lower: Int, val higher: Int)