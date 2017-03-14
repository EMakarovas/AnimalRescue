package com.emakarovas.animalrescue.mail

import com.emakarovas.animalrescue.model.UserModel
import com.google.inject.ImplementedBy

import play.api.libs.mailer.Attachment
import play.api.libs.mailer.Email
import play.api.libs.mailer.MailerClient
import com.emakarovas.animalrescue.mail.impl.DefaultMailer

@ImplementedBy(classOf[DefaultMailer])
trait Mailer {
  
  protected def appEmail: String
  protected def mailerClient: MailerClient
  
  def sendRegistrationEmail(user: UserModel): Unit
  
  protected def sendEmail(to: String, subject: String, bodyText: String, bodyHtml: String, attachments: Seq[Attachment]) = {
    val email = Email(
        subject = subject,
        from = appEmail,
        to = Seq(s"<$to"),
        bodyText = Some(bodyText),
        bodyHtml = Some(bodyHtml),
        charset = Some("UTF-8"),
        attachments = attachments)
    mailerClient.send(email)
  }
  
}