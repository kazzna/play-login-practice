package controllers

import play.api.mvc.Controller
import jp.t2v.lab.play2.auth.AuthConfig
import play.api.mvc.{Result, RequestHeader}

import scala.concurrent.{Future, ExecutionContext}
import scala.reflect.classTag

trait AuthConfigImpl /*extends AuthConfig with Controller {
  override type Id = String
  override type Authority = String
  override type User = models.User
  val sessionTimeoutInSeconds = 3600

  override def resolveUser(id: Id)(implicit context: ExecutionContext) =
    Future(models.User.selectById(id))

  def authorize(user: User, authority: Authority)(implicit context: ExecutionContext) =
    Future(true)

  override def logoutSucceeded(request: RequestHeader)(implicit context: ExecutionContext) =
  Future(Redirect(routes.Application.login))

  override def authenticationFailed(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] =
  Future(Redirect(routes.Application.login))

  override implicit def idTag = classTag[Id]

  override def loginSucceeded(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] =
  Future(Redirect(routes.Application.member))

  def authorizationFailed(request: RequestHeader, user: User, authority: Option[Authority])(implicit context: ExecutionContext): Future[Result] =
  Future(Forbidden("Not permitted."))

}*/
