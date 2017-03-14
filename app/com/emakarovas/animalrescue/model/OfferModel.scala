package com.emakarovas.animalrescue.model

import java.util.Date

case class OfferModel
  (override val id: String,
   override val url: String,
   startDate: Date,
   endDate: Option[Date],
   text: String,
   image: Option[ImageModel],
   video: Option[VideoModel],
   location: LocationModel,
   commentList: List[CommentModel],
   viewedByUserIdList: List[String],
   userId: String)
   // also has a list of animalmodels in their own collection
   extends AbstractModel(id) with AbstractURLAccessibleEntity with AbstractPersistableEntity