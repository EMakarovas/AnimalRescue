package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.AdoptionDetailsModel
import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.OfferDetailsModel
import com.emakarovas.animalrescue.model.OfferTerminationReasonModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.AdoptionDetailsConstants
import com.emakarovas.animalrescue.model.constants.AnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferDetailsConstants
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
class AnimalReader @Inject() 
  (implicit val animalTypeReader: AnimalTypeReader,
   implicit val genderReader: GenderReader,
   implicit val imageReader: ImageReader,
   implicit val videoReader: VideoReader,
   implicit val offerTerminationReasonEnumReader: OfferTerminationReasonReader) extends AbstractEntityReader[AnimalModel] {
  
  implicit object offerTerminationReasonReader extends BSONDocumentReader[OfferTerminationReasonModel] {
    override def read(doc: BSONDocument): OfferTerminationReasonModel = {
      val offerTerminationReason = doc.getAs[OfferTerminationReason](OfferTerminationReasonConstants.OfferTerminationReason).get
      val textOpt = doc.getAs[String](OfferTerminationReasonConstants.Text)
      OfferTerminationReasonModel(offerTerminationReason, textOpt)
    }
  }

  implicit object offerDetailsReader extends BSONDocumentReader[OfferDetailsModel] {
    override def read(doc: BSONDocument): OfferDetailsModel = {
      val foodCostOpt = doc.getAs[Double](OfferDetailsConstants.FoodCost)
      val shelterCostOpt = doc.getAs[Double](OfferDetailsConstants.ShelterCost)
      val vaccinationCostOpt = doc.getAs[Double](OfferDetailsConstants.VaccinationCost)
      val offerId = doc.getAs[String](OfferDetailsConstants.OfferId).get
      val offerTerminationReasonOpt = doc.getAs[OfferTerminationReasonModel](OfferDetailsConstants.OfferTerminationReason)
      OfferDetailsModel(foodCostOpt, shelterCostOpt, vaccinationCostOpt, offerId, offerTerminationReasonOpt)
    }
  }
  
  implicit object adoptionDetailsReader extends BSONDocumentReader[AdoptionDetailsModel] {
    override def read(doc: BSONDocument): AdoptionDetailsModel = {
      val ownerIdOpt = doc.getAs[String](AdoptionDetailsConstants.OwnerId)
      val date = doc.getAs[Date](AdoptionDetailsConstants.Date).get
      AdoptionDetailsModel(ownerIdOpt, date)
    }
  }

  override def read(doc: BSONDocument): AnimalModel = {
    val id = doc.getAs[String](MongoConstants.MongoId).get
    val animalType = doc.getAs[AnimalType](AnimalConstants.AnimalType).get
    val specificTypeOpt = doc.getAs[String](AnimalConstants.SpecificType)
    val gender = doc.getAs[Gender](AnimalConstants.Gender).get
    val nameOpt = doc.getAs[String](AnimalConstants.Name)
    val ageOpt = doc.getAs[Int](AnimalConstants.Age)
    val descriptionOpt = doc.getAs[String](AnimalConstants.Description)
    val imageOpt = doc.getAs[ImageModel](AnimalConstants.Image)
    val videoOpt = doc.getAs[VideoModel](AnimalConstants.Video)
    val adoptionDetailsOpt = doc.getAs[AdoptionDetailsModel](AnimalConstants.AdoptionDetails)
    val offerDetailsOpt = doc.getAs[OfferDetailsModel](AnimalConstants.OfferDetails)
    AnimalModel(id, animalType, specificTypeOpt, gender, nameOpt, ageOpt, descriptionOpt, imageOpt, videoOpt,
        adoptionDetailsOpt, offerDetailsOpt)
  }
  
}