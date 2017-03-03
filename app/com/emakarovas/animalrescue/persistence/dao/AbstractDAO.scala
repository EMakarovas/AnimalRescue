package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractEntity
import com.emakarovas.animalrescue.persistence.reader.AbstractEntityReader
import com.emakarovas.animalrescue.persistence.writer.AbstractEntityWriter

import reactivemongo.api.collections.bson.BSONCollection

trait AbstractDAO[T <: AbstractEntity] {
  
  protected val collection: Future[BSONCollection]
  protected implicit def writer: AbstractEntityWriter[T]
  protected implicit def reader: AbstractEntityReader[T]  
 
}