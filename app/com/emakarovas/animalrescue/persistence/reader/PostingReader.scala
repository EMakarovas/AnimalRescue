package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.PostingModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import java.util.Date
import javax.inject.Inject
import com.emakarovas.animalrescue.model.GeolocationModel
import com.emakarovas.animalrescue.model.CostModel

@Singleton
class PostingReader @Inject() (
    implicit val costReader: CostReader,
    implicit val geolocationReader: GeolocationReader) extends AbstractModelReader[PostingModel] {
  
  def read(doc: BSONDocument): PostingModel = {
    val id = doc.getAs[String]("_id").get
    val startDate = doc.getAs[Date]("startDate").get
    val endDate = doc.getAs[Date]("endDate")
    val text = doc.getAs[String]("text").get
    val costList = doc.getAs[List[CostModel]]("costList").get
    val geolocation = doc.getAs[GeolocationModel]("geolocation").get
    val userId = doc.getAs[String]("userId").get
    val available = doc.getAs[Boolean]("available").get
    PostingModel(id, startDate, endDate, text, costList, geolocation, userId, available)
  }
}

