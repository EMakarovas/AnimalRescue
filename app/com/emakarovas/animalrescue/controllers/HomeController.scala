package com.emakarovas.animalrescue.controllers

import scala.concurrent.Future

import javax.inject.Singleton
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import play.api.mvc.Controller
import javax.inject.Inject

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() () extends Controller {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request => 
    Ok(views.html.index())
  }
  
   
  
   def async = Action.async {
     val s = "Hello"
     val f: Future[String] = Future {
       println(System.currentTimeMillis())
       Thread.sleep(3000);
       s + " future!"
     }
     f onSuccess {
       case msg => println(msg)
     }
     f.map { pi =>
       println(System.currentTimeMillis())
      Ok("PI value computed: " + pi)
    }
   }
//
//  def index = Action.async {
//    val futureInt = scala.concurrent.Future { intensiveComputation() }
//    futureInt.map(i => Ok("Got result: " + i))
//  }
  
}
