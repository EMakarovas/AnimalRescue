package com.emakarovas.animalrescue.persistence.dao.search

import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.emakarovas.animalrescue.persistence.dao.constants.FacetConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.reader.enumeration.AnimalTypeReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.ColorReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.SizeReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.SpecificTypeReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONArray

@Singleton
class OfferFacetReader @Inject() 
  (implicit val animalTypeReader: AnimalTypeReader,
   implicit val specificTypeReader: SpecificTypeReader,
   implicit val genderReader: GenderReader,
   implicit val colorReader: ColorReader,
   implicit val sizeReader: SizeReader) extends BSONDocumentReader[OfferFacet] {
  
  implicit object AnimalTypeFacetCategoryReader extends BSONDocumentReader[AnimalTypeFacetCategory] {
    override def read(doc: BSONDocument): AnimalTypeFacetCategory = {
      val category = doc.getAs[AnimalType[_ <: Animal]](MongoConstants.MongoId).get
      val count = doc.getAs[Int](FacetConstants.Count).get
      AnimalTypeFacetCategory(category, count)
    }
  }
  
  implicit object SpecificTypeFacetCategoryReader extends BSONDocumentReader[SpecificTypeFacetCategory] {
    override def read(doc: BSONDocument): SpecificTypeFacetCategory = {
      val category = doc.getAs[SpecificType[_ <: Animal]](MongoConstants.MongoId).get
      val count = doc.getAs[Int](FacetConstants.Count).get
      SpecificTypeFacetCategory(category, count)
    }
  }
  
  implicit object GenderFacetCategoryReader extends BSONDocumentReader[GenderFacetCategory] {
    override def read(doc: BSONDocument): GenderFacetCategory = {
      val category = doc.getAs[Gender](MongoConstants.MongoId).get
      val count = doc.getAs[Int](FacetConstants.Count).get
      GenderFacetCategory(category, count)
    }
  }
  
  implicit object ColorFacetCategoryReader extends BSONDocumentReader[ColorFacetCategory] {
    override def read(doc: BSONDocument): ColorFacetCategory = {
      val category = doc.getAs[Color](MongoConstants.MongoId).get
      val count = doc.getAs[Int](FacetConstants.Count).get
      ColorFacetCategory(category, count)
    }
  }
  
  implicit object SizeFacetCategoryReader extends BSONDocumentReader[SizeFacetCategory] {
    override def read(doc: BSONDocument): SizeFacetCategory = {
      val category = doc.getAs[Size](MongoConstants.MongoId).get
      val count = doc.getAs[Int](FacetConstants.Count).get
      SizeFacetCategory(category, count)
    }
  }
  
  implicit object TagFacetCategoryReader extends BSONDocumentReader[TagFacetCategory] {
    override def read(doc: BSONDocument): TagFacetCategory = {
      val category = doc.getAs[String](MongoConstants.MongoId).get
      val count = doc.getAs[Int](FacetConstants.Count).get
      TagFacetCategory(category, count)
    }
  }
  
  implicit object CountryFacetCategoryReader extends BSONDocumentReader[CountryFacetCategory] {
    override def read(doc: BSONDocument): CountryFacetCategory = {
      val category = doc.getAs[String](MongoConstants.MongoId).get
      val count = doc.getAs[Int](FacetConstants.Count).get
      CountryFacetCategory(category, count)
    }
  }
  
  implicit object CityFacetCategoryReader extends BSONDocumentReader[CityFacetCategory] {
    override def read(doc: BSONDocument): CityFacetCategory = {
      val category = doc.getAs[String](MongoConstants.MongoId).get
      val count = doc.getAs[Int](FacetConstants.Count).get
      CityFacetCategory(category, count)
    }
  }
  
  implicit object CastratedFacetCategoryReader extends BSONDocumentReader[CastratedFacetCategory] {
    override def read(doc: BSONDocument): CastratedFacetCategory = {
      val category = doc.getAs[Boolean](MongoConstants.MongoId).get
      val count = doc.getAs[Int](FacetConstants.Count).get
      CastratedFacetCategory(category, count)
    }
  }

  override def read(doc: BSONDocument): OfferFacet = {
    val animalTypeSet = doc.getAs[Set[AnimalTypeFacetCategory]](FacetConstants.AnimalType).get
    val specificTypeSet = doc.getAs[Set[SpecificTypeFacetCategory]](FacetConstants.SpecificType).get
    val genderSet = doc.getAs[Set[GenderFacetCategory]](FacetConstants.Gender).get
    val colorSet = doc.getAs[Set[ColorFacetCategory]](FacetConstants.Color).get
    val sizeSet = doc.getAs[Set[SizeFacetCategory]](FacetConstants.Size).get
    val tagSet = doc.getAs[Set[TagFacetCategory]](FacetConstants.Tag).get
    val countrySet = doc.getAs[Set[CountryFacetCategory]](FacetConstants.Country).get
    val citySet = doc.getAs[Set[CityFacetCategory]](FacetConstants.City).get
    val castratedSet = doc.getAs[Set[CastratedFacetCategory]](FacetConstants.Castrated).get
    OfferFacet(animalTypeSet, specificTypeSet, genderSet, colorSet, sizeSet, tagSet, countrySet, citySet, castratedSet)
  }
  
}