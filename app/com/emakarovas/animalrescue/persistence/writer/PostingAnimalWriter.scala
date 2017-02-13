package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.PostingAnimalModel
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class PostingAnimalWriter @Inject() (
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val genderWriter: GenderWriter,
    implicit val imageWriter: ImageWriter) extends AbstractModelWriter[PostingAnimalModel] {

  override def write(postingAnimal: PostingAnimalModel): BSONDocument = {
    BSONDocument(
        "_id" -> postingAnimal.id,
        "animalType" -> postingAnimal.animalType,
        "specificType" -> postingAnimal.specificType,
        "name" -> postingAnimal.name,
        "gender" -> postingAnimal.gender,
        "age" -> postingAnimal.age,
        "description" -> postingAnimal.description,
        "image" -> postingAnimal.image,
        "postingId" -> postingAnimal.postingId)
  }
}