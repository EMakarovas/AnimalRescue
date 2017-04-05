package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.AnimalTypeDetails
import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.Location
import com.emakarovas.animalrescue.model.OfferAnimalModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.OfferTerminationReasonModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.model.constants.OfferTerminationReasonConstants
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.reader.enumeration.ColorReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.OfferTerminationReasonReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.SizeReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONArray

@Singleton
class OfferReader @Inject() (
    implicit val imageReader: ImageReader,
    implicit val videoReader: VideoReader,
    implicit val locationReader: LocationReader,
    implicit val commentReader: CommentReader,
    implicit val offerTerminationReasonEnumReader: OfferTerminationReasonReader,
    implicit val animalTypeDetailsReader: AnimalTypeDetailsReader,
    implicit val genderReader: GenderReader,
    implicit val colorReader: ColorReader,
    implicit val sizeReader: SizeReader) extends AbstractEntityReader[OfferModel] {
  
  implicit object offerTerminationReasonReader extends BSONDocumentReader[OfferTerminationReasonModel] {
    override def read(doc: BSONDocument): OfferTerminationReasonModel = {
      val offerTerminationReason = doc.getAs[OfferTerminationReason](OfferTerminationReasonConstants.OfferTerminationReason).get
      val textOpt = doc.getAs[String](OfferTerminationReasonConstants.Text)
      OfferTerminationReasonModel(offerTerminationReason, textOpt)
    }
  }

  implicit object offerAnimalReader extends BSONDocumentReader[OfferAnimalModel] {
    override def read(doc: BSONDocument): OfferAnimalModel = {
      val id = doc.getAs[String](MongoConstants.MongoId).get
      val animalTypeDetails = doc.getAs[AnimalTypeDetails[_ <: Animal]](OfferAnimalConstants.AnimalTypeDetails)(animalTypeDetailsReader).get
      val genderOpt = doc.getAs[Gender](OfferAnimalConstants.Gender)
      val nameOpt = doc.getAs[String](OfferAnimalConstants.Name)
      val age = doc.getAs[Int](OfferAnimalConstants.Age).get
      val descriptionOpt = doc.getAs[String](OfferAnimalConstants.Description)
      val colorSet = doc.getAs[Set[Color]](OfferAnimalConstants.ColorSet).get
      val size = doc.getAs[Size](OfferAnimalConstants.Size).get
      val tagSet = doc.getAs[Set[String]](OfferAnimalConstants.TagSet).get
      val isCastrated = doc.getAs[Boolean](OfferAnimalConstants.IsCastrated).get
      val imageOpt = doc.getAs[ImageModel](OfferAnimalConstants.Image)
      val videoOpt = doc.getAs[VideoModel](OfferAnimalConstants.Video)
      val castrationCostOpt = doc.getAs[Double](OfferAnimalConstants.CastrationCost)
      val foodCostOpt = doc.getAs[Double](OfferAnimalConstants.FoodCost)
      val shelterCostOpt = doc.getAs[Double](OfferAnimalConstants.ShelterCost)
      val vaccinationCostOpt = doc.getAs[Double](OfferAnimalConstants.VaccinationCost)
      val offerTerminationReasonOpt = doc.getAs[OfferTerminationReasonModel](OfferAnimalConstants.OfferTerminationReason)
      OfferAnimalModel(id, animalTypeDetails, genderOpt, nameOpt, age, descriptionOpt, colorSet, size, tagSet, isCastrated,
          imageOpt, videoOpt, castrationCostOpt, foodCostOpt, shelterCostOpt, vaccinationCostOpt, offerTerminationReasonOpt)
    }
  }
  
  def read(doc: BSONDocument): OfferModel = {
    val id = doc.getAs[String](MongoConstants.MongoId).get
    val url = doc.getAs[String](OfferConstants.Url).get
    val startDate = doc.getAs[Date](OfferConstants.StartDate).get
    val endDate = doc.getAs[Date](OfferConstants.EndDate)
    val offerAnimalList = doc.getAs[List[OfferAnimalModel]]("offerAnimalList").toList.flatten
    val text = doc.getAs[String](OfferConstants.Text).get
    val imageOpt = doc.getAs[ImageModel](OfferConstants.Image)
    val videoOpt = doc.getAs[VideoModel](OfferConstants.Video)
    val location = doc.getAs[Location](OfferConstants.Location).get
    val commentList = doc.getAs[List[CommentModel]](OfferConstants.CommentList).get
    val viewedByUserIdSet = doc.getAs[Set[String]](OfferConstants.ViewedByUserIdSet).get
    val userId = doc.getAs[String]("userId").get
    OfferModel(id, url, startDate, endDate, text, offerAnimalList, imageOpt, videoOpt, location, 
        commentList, viewedByUserIdSet, userId)
  }
}