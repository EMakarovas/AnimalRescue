package com.emakarovas.animalrescue.mail.impl

import com.emakarovas.animalrescue.mail.Mailer
import com.emakarovas.animalrescue.model.UserModel

import javax.inject.Inject
import play.api.libs.mailer.MailerClient

class DefaultMailer @Inject() (
    configuration: play.api.Configuration,
    override protected val mailerClient: MailerClient) extends Mailer {
  
  override protected val appEmail = configuration.underlying.getString("mail.from")
  
  override def sendRegistrationEmail(user: UserModel): Unit = {
    val to = user.email
    val subject = "Registration email subject"
    val bodyText = getRegistrationBodyText()
    val bodyHtml = getRegistrationBodyHtml()
    val attachments = Seq.empty
    sendEmail(to, subject, bodyText, bodyHtml, attachments)
  }
   
  private def getRegistrationBodyText(): String = {
    "body text"
  }
  
  private def getRegistrationBodyHtml(): String = {
    "body html"
  }
  
}