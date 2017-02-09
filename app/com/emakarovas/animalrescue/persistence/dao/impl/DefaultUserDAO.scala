package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.UserDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.UserModelCollectionType
import com.emakarovas.animalrescue.persistence.reader.UserReader
import com.emakarovas.animalrescue.persistence.writer.UserWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUserDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: UserWriter,
    implicit val reader: UserReader) extends UserDAO {
  
 val collection = colFactory.getCollection(UserModelCollectionType)
  
}