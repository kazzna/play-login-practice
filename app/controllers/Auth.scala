package controllers

import jp.t2v.lab.play2.auth.LoginLogout
import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by kazzna on 15/09/30.
 */
class Auth extends Controller with LoginLogout with AuthConfigImpl {
  val loginForm = Form {
    mapping(
      "user-id" -> text,
      "password" -> text
    )(User.authenticate)(_.flatMap(User.unapply))
      .verifying("Invalid userId or password", result => result.isDefined)
  }

  val authError = Json.parse( """{"errors":[{"message":"Autherization failed."}]}""")

  def login = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithError => Future.successful(Forbidden(authError)),
      user => gotoLoginSucceeded(user.get.id)
    )
  }

  def logout = Action.async { implicit request =>
    gotoLogoutSucceeded
  }
}
