package controllers

import jp.t2v.lab.play2.auth.AuthElement
import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

class Application extends Controller with AuthElement with AuthConfigImpl {
  val loginForm = Form {
    mapping(
      "user-id" -> text,
      "password" -> text
    )(User.authenticate)(_.flatMap(User.unapply))
      .verifying("Invalid userId or password", result => result.isDefined)
  }

  def index = StackAction(AuthorityKey -> true) { implicit request =>
    val user = loggedIn
    val msg = s"""{"res": "Hello, ${user.id}."}"""
    val json = Json.parse(msg)
    Ok(json)
  }
}
