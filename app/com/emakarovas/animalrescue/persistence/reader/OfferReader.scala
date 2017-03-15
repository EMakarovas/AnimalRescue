package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.OfferAnimalModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.OfferTerminationReasonModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.model.constants.OfferTerminationReasonConstants
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.reader.enumeration.AnimalTypeReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.OfferTerminationReasonReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader

@Singleton
class OfferReader @Inject() (
    implicit val imageReader: ImageReader,
    implicit val videoReader: VideoReader,
    implicit val locationReader: LocationReader,
    implicit val commentReader: CommentReader,
    implicit val offerTerminationReasonEnumReader: OfferTerminationReasonReader,
    implicit val animalTypeReader: AnimalTypeReader,
    implicit val genderReader: GenderReader) extends AbstractEntityReader[OfferModel] {
  
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
      val animalType = doc.getAs[AnimalType](OfferAnimalConstants.AnimalType).get
      val specificTypeOpt = doc.getAs[String](OfferAnimalConstants.SpecificType)
      val gender = doc.getAs[Gender](OfferAnimalConstants.Gender).get
      val nameOpt = doc.getAs[String](OfferAnimalConstants.Name)
      val ageOpt = doc.getAs[Int](OfferAnimalConstants.Age)
      val descriptionOpt = doc.getAs[String](OfferAnimalConstants.Description)
      val isCastrated = doc.getAs[Boolean](OfferAnimalConstants.IsCastrated).get
      val imageOpt = doc.getAs[ImageModel](OfferAnimalConstants.Image)
      val videoOpt = doc.getAs[VideoModel](OfferAnimalConstants.Video)
      val castrationCostOpt = doc.getAs[Double](OfferAnimalConstants.CastrationCost)
      val foodCostOpt = doc.getAs[Double](OfferAnimalConstants.FoodCost)
      val shelterCostOpt = doc.getAs[Double](OfferAnimalConstants.ShelterCost)
      val vaccinationCostOpt = doc.getAs[Double](OfferAnimalConstants.VaccinationCost)
      val offerTerminationReasonOpt = doc.getAs[OfferTerminationReasonModel](OfferAnimalConstants.OfferTerminationReason)
      OfferAnimalModel(id, animalType, specificTypeOpt, gender, nameOpt, ageOpt, descriptionOpt, isCastrated,
          imageOpt, videoOpt, castrationCostOpt, foodCostOpt, shelterCostOpt, vaccinationCostOpt, offerTerminationReasonOpt)
    }
  }
  
  def read(topDoc: BSONDocument): OfferModel = {
    val id = topDoc.getAs[String](MongoConstants.MongoId).get
    val doc = topDoc.getAs[BSONDocument](MongoConstants.Data).get
    val url = doc.getAs[String](OfferConstants.Url).get
    val startDate = doc.getAs[Date](OfferConstants.StartDate).get
    val endDate = doc.getAs[Date](OfferConstants.EndDate)
    val offerAnimalList = doc.getAs[List[OfferAnimalModel]](OfferConstants.OfferAnimalList).get
    val text = doc.getAs[String](OfferConstants.Text).get
    val imageOpt = doc.getAs[ImageModel](OfferConstants.Image)
    val videoOpt = doc.getAs[VideoModel](OfferConstants.Video)
    val location = doc.getAs[LocationModel](OfferConstants.Location).get
    val commentList = doc.getAs[List[CommentModel]](OfferConstants.CommentList).get
    val viewedByUserIdList = doc.getAs[List[String]](OfferConstants.ViewedByUserIdList).get
    val userId = doc.getAs[String]("userId").get
    OfferModel(id, url, startDate, endDate, text, offerAnimalList, imageOpt, videoOpt, location, 
        commentList, viewedByUserIdList, userId)
  }
}