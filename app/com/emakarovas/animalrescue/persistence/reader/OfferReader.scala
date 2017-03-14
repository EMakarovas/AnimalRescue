package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class OfferReader @Inject() (
    implicit val imageReader: ImageReader,
    implicit val videoReader: VideoReader,
    implicit val locationReader: LocationReader,
    implicit val commentReader: CommentReader) extends AbstractEntityReader[OfferModel] {
  
  def read(topDoc: BSONDocument): OfferModel = {
    val id = topDoc.getAs[String](MongoConstants.MongoId).get
    val doc = topDoc.getAs[BSONDocument](MongoConstants.Data).get
    val url = doc.getAs[String](OfferConstants.Url).get
    val startDate = doc.getAs[Date](OfferConstants.StartDate).get
    val endDate = doc.getAs[Date](OfferConstants.EndDate)
    val text = doc.getAs[String](OfferConstants.Text).get
    val imageOpt = doc.getAs[ImageModel](OfferConstants.Image)
    val videoOpt = doc.getAs[VideoModel](OfferConstants.Video)
    val location = doc.getAs[LocationModel](OfferConstants.Location).get
    val commentList = doc.getAs[List[CommentModel]](OfferConstants.CommentList).get
    val viewedByUserIdList = doc.getAs[List[String]](OfferConstants.ViewedByUserIdList).get
    val userId = doc.getAs[String]("userId").get
    OfferModel(id, url, startDate, endDate, text, imageOpt, videoOpt, location, commentList, viewedByUserIdList, userId)
  }
}