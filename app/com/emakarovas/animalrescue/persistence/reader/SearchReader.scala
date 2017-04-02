package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.AnimalTypeDetails
import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.Location
import com.emakarovas.animalrescue.model.SearchAnimalModel
import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.SearchTerminationReasonModel
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.model.constants.SearchTerminationReasonConstants
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.SearchTerminationReason
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.reader.enumeration.ColorReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.SearchTerminationReasonReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.SizeReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader

@Singleton
class SearchReader @Inject() (
    implicit val animalTypeDetailsReader: AnimalTypeDetailsReader,
    implicit val genderReader: GenderReader,
    implicit val locationReader: LocationReader,
    implicit val commentReader: CommentReader,
    implicit val searchTerminationReasonEnumReader: SearchTerminationReasonReader,
    implicit val colorReader: ColorReader,
    implicit val sizeReader: SizeReader) extends AbstractEntityReader[SearchModel] {
  
  implicit object searchTerminationReasonReader extends BSONDocumentReader[SearchTerminationReasonModel] {
    override def read(doc: BSONDocument): SearchTerminationReasonModel = {
      val searchTerminationReason = doc.getAs[SearchTerminationReason](SearchTerminationReasonConstants.SearchTerminationReason).get
      val textOpt = doc.getAs[String](SearchTerminationReasonConstants.Text)
      SearchTerminationReasonModel(searchTerminationReason, textOpt)
    }
  }

  implicit object searchAnimalReader extends BSONDocumentReader[SearchAnimalModel] {
    override def read(doc: BSONDocument): SearchAnimalModel = {
      val id = doc.getAs[String](MongoConstants.MongoId).get
      val animalTypeDetails = doc.getAs[AnimalTypeDetails[_ <: Animal]](SearchAnimalConstants.AnimalTypeDetails).get
      val genderOpt = doc.getAs[Gender](SearchAnimalConstants.Gender)
      val colorSet = doc.getAs[Set[Color]](SearchAnimalConstants.ColorSet).get
      val sizeSet = doc.getAs[Set[Size]](SearchAnimalConstants.SizeSet).get
      val tagSet = doc.getAs[Set[String]](SearchAnimalConstants.TagSet).get
      val castratedOnly = doc.getAs[Boolean](SearchAnimalConstants.CastratedOnly).get
      val minAge = doc.getAs[Int](SearchAnimalConstants.MinAge)
      val maxAge = doc.getAs[Int](SearchAnimalConstants.MaxAge)
      val potentialAnimalIdList = doc.getAs[List[String]](SearchAnimalConstants.PotentialAnimalIdList).get
      val searchTerminationReasonOpt = doc.getAs[SearchTerminationReasonModel](SearchAnimalConstants.SearchTerminationReason)
      SearchAnimalModel(id, animalTypeDetails, genderOpt, colorSet, sizeSet, tagSet, 
          castratedOnly, minAge, maxAge, potentialAnimalIdList, searchTerminationReasonOpt)
    }
  }

  override def read(doc: BSONDocument): SearchModel = {
    val id = doc.getAs[String](MongoConstants.MongoId).get
    val url = doc.getAs[String](SearchConstants.Url).get
    val searchAnimalList = doc.getAs[List[SearchAnimalModel]](SearchConstants.SearchAnimalList).get
    val location = doc.getAs[Location](SearchConstants.Location).get
    val commentList = doc.getAs[List[CommentModel]](SearchConstants.CommentList).get
    val startDate = doc.getAs[Date](SearchConstants.StartDate).get
    val endDate = doc.getAs[Date](SearchConstants.EndDate)
    val isPublic = doc.getAs[Boolean](SearchConstants.IsPublic).get
    val userId = doc.getAs[String](SearchConstants.UserId).get
    SearchModel(id, url, searchAnimalList, location, commentList, startDate, endDate, isPublic, userId)
  }
  
}