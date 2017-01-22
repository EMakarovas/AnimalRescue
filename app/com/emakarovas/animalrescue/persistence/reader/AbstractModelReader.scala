package com.emakarovas.animalrescue.persistence.reader

import reactivemongo.bson.BSONDocumentReader

// Might be useful later on
abstract class AbstractModelReader[T] extends BSONDocumentReader[T] {

}