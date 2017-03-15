package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.OfferAnimalModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.OfferTerminationReasonModel
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.model.constants.OfferTerminationReasonConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.OfferTerminationReasonWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants

@Singleton
class OfferWriter @Inject() (
    implicit private val offerAnimalWriter: OfferAnimalWriter,
    implicit private val imageWriter: ImageWriter,
    implicit private val videoWriter: VideoWriter,
    implicit private val locationWriter: LocationWriter,
    implicit private val commentWriter: CommentWriter,
    implicit private val offerTerminationReasonEnumWriter: OfferTerminationReasonWriter,
    implicit private val animalTypeWriter: AnimalTypeWriter,
    implicit val genderWriter: GenderWriter) extends AbstractEntityWriter[OfferModel] {

  override def write(offer: OfferModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> offer.id,
        MongoConstants.Data -> BSONDocument(
            OfferConstants.Url -> offer.url,
            OfferConstants.StartDate -> offer.startDate,
            OfferConstants.EndDate -> offer.endDate,
            OfferConstants.Text -> offer.text,
            OfferConstants.OfferAnimalList -> offer.offerAnimalList,
            OfferConstants.Image -> offer.image,
            OfferConstants.Video -> offer.video,
            OfferConstants.Location -> offer.location,
            OfferConstants.CommentList -> offer.commentList,
            OfferConstants.ViewedByUserIdList -> offer.viewedByUserIdList,
            OfferConstants.UserId -> offer.userId))
  }
}