package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class OfferWriter @Inject() (
    implicit val imageWriter: ImageWriter,
    implicit val videoWriter: VideoWriter,
    implicit val locationWriter: LocationWriter,
    implicit val commentWriter: CommentWriter) extends AbstractEntityWriter[OfferModel] {
  override def write(offer: OfferModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> offer.id,
        OfferConstants.Url -> offer.url,
        OfferConstants.StartDate -> offer.startDate,
        OfferConstants.EndDate -> offer.endDate,
        OfferConstants.Text -> offer.text,
        OfferConstants.Image -> offer.image,
        OfferConstants.Video -> offer.video,
        OfferConstants.Location -> offer.location,
        OfferConstants.CommentList -> offer.commentList,
        OfferConstants.ViewedByUserIdList -> offer.viewedByUserIdList,
        OfferConstants.UserId -> offer.userId)
  }
}