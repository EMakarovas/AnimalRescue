package com.emakarovas.animalrescue.persistence.writer

import reactivemongo.bson.BSONDocumentWriter

abstract class AbstractModelWriter[T] extends BSONDocumentWriter[T] {

}