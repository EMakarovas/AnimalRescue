package com.emakarovas.animalrescue.persistence.dao.update

case class UpdateResult(
  updateStatus: UpdateStatus,
  // contains the number of documents updated
  n: Int)