package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.SearchAnimalModel
import com.emakarovas.animalrescue.model.SearchTerminationReasonModel
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants
import com.emakarovas.animalrescue.model.constants.SearchTerminationReasonConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.SearchTerminationReasonWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.writer.enumeration.SizeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.ColorWriter

@Singleton
class SearchAnimalWriter @Inject() (
    implicit private val animalTypeDetailsWriter: AnimalTypeDetailsWriter,
    implicit private val genderWriter: GenderWriter,
    implicit val searchTerminationReasonEnumWriter: SearchTerminationReasonWriter,
    implicit val colorWriter: ColorWriter,
    implicit val sizeWriter: SizeWriter)
      extends AbstractEntityWriter[SearchAnimalModel] {
      
  implicit object searchTerminationReasonWriter extends AbstractEntityWriter[SearchTerminationReasonModel] {
    override def write(searchTerminationReason: SearchTerminationReasonModel): BSONDocument = {
      BSONDocument(
          SearchTerminationReasonConstants.SearchTerminationReason -> searchTerminationReason.searchTerminationReason,
          SearchTerminationReasonConstants.Text -> searchTerminationReason.text)
    }
  }
      
  override def write(searchAnimal: SearchAnimalModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> searchAnimal.id,
        SearchAnimalConstants.AnimalTypeDetails -> animalTypeDetailsWriter.write(searchAnimal.animalTypeDetails),
        SearchAnimalConstants.Gender -> searchAnimal.gender,
        SearchAnimalConstants.ColorSet -> searchAnimal.colorSet,
        SearchAnimalConstants.SizeSet -> searchAnimal.sizeSet,
        SearchAnimalConstants.TagSet -> searchAnimal.tagSet,
        SearchAnimalConstants.MinAge -> searchAnimal.minAge,
        SearchAnimalConstants.MaxAge -> searchAnimal.maxAge,
        SearchAnimalConstants.PotentialAnimalIdList -> searchAnimal.potentialAnimalIdList,
        SearchAnimalConstants.SearchTerminationReason -> searchAnimal.searchTerminationReason)
  }
}