package com.emakarovas.animalrescue.persistence.writer

import java.util.Date

import com.emakarovas.animalrescue.model.GeolocationModel
import com.emakarovas.animalrescue.model.WishModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter

@Singleton
class WishWriter @Inject() (
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val genderWriter: GenderWriter,
    implicit val geolocationWriter: GeolocationWriter) extends AbstractModelWriter[WishModel] {
  def write(wish: WishModel): BSONDocument = {
    BSONDocument(
        "_id" -> wish.id,
        "animalType" -> wish.animalType,
        "specificType" -> wish.specificType,
        "gender" -> wish.gender,
        "minAge" -> wish.minAge,
        "maxAge" -> wish.maxAge,
        "geolocation" -> wish.geolocation,
        "startDate" -> wish.startDate,
        "endDate" -> wish.endDate,
        "userId" -> wish.userId)
  }
}