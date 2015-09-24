package controllers

import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

class Application extends Controller /*with LoginLogout with AuthConfigImpl*/ {
  val loginForm = Form {
    mapping("user-id" -> text, "password" -> text)(User.authenticate)(_.flatMap(User.unapply))
      .verifying("Invalid userId or password", result => result.isDefined)
  }

  def index(msg: String = "") = Action { implicit request =>
    println("index is called.")
    request.session.get("sessionId").map { id =>
      println(s"in index, sessionId is [$id].")
      Ok(views.html.member(id))
    }.getOrElse {
      println(s"in index, sessionId is missing.")
      Ok(views.html.login(msg))
    }
  }

  def authenticate = Action { implicit request =>
    println("authenticate is called.")
    val hasErrors: Form[Option[User]] => Result = form => {
      println(s"in authenticate form has error [${form.errors}].")
      Redirect(routes.Application.index)
    }
    val success: Option[User] => Result = user => {
      user.map { u =>
        println(s"in authenticate user is [${u.id}}].")
        Redirect(routes.Application.index).withSession {
          request.session + ("sessionId" -> u.id)
        }
      }.getOrElse {
        println(s"in authenticate user not found.")
        NotFound
      }
    }
    loginForm.bindFromRequest().fold(hasErrors, success)
  }

  def logout = Action {
    println("logout is called.")
    Redirect(routes.Application.index).withNewSession
  }
}
