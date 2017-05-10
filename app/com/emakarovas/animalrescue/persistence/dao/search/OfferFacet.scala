package com.emakarovas.animalrescue.persistence.dao.search

import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.model.enumeration.SpecificType

/**
 * The class containing facet search values for offers
 */
case class OfferFacet(
    animalTypeSet: Set[AnimalTypeFacetCategory],
    specificTypeSet: Set[SpecificTypeFacetCategory],
    genderSet: Set[GenderFacetCategory],
    colorSet: Set[ColorFacetCategory],
    sizeSet: Set[SizeFacetCategory],
    tagSet: Set[TagFacetCategory],
    countrySet: Set[CountryFacetCategory],
    citySet: Set[CityFacetCategory],
    castratedSet: Set[CastratedFacetCategory])
    
sealed trait FacetCategory[T <: Any] {
   def category: T
   def count: Int
}
    
case class AnimalTypeFacetCategory(category: AnimalType[_ <: Animal], count: Int) extends FacetCategory[AnimalType[_]]
case class SpecificTypeFacetCategory(category: SpecificType[_ <: Animal], count: Int) extends FacetCategory[SpecificType[_]]
case class GenderFacetCategory(category: Gender, count: Int) extends FacetCategory[Gender]
case class ColorFacetCategory(category: Color, count: Int) extends FacetCategory[Color]
case class SizeFacetCategory(category: Size, count: Int) extends FacetCategory[Size]
case class TagFacetCategory(category: String, count: Int) extends FacetCategory[String]
case class CountryFacetCategory(category: String, count: Int) extends FacetCategory[String]
case class CityFacetCategory(category: String, count: Int) extends FacetCategory[String]
case class CastratedFacetCategory(category: Boolean, count: Int) extends FacetCategory[Boolean]