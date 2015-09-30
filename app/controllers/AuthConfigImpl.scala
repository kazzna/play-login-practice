package controllers

import jp.t2v.lab.play2.auth.AuthConfig
import play.api.mvc.Results.{Forbidden, NotFound, Redirect}
import play.api.mvc.{RequestHeader, Result}

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.classTag

trait AuthConfigImpl extends AuthConfig {
  override type Id = String
  override type Authority = Boolean
  override type User = models.User
  private val accessUri = "access-uri"
  val sessionTimeoutInSeconds = 3600

  override def resolveUser(id: Id)(implicit context: ExecutionContext) =
    Future(models.User.selectById(id))

  def authorize(user: User, authority: Authority)(implicit context: ExecutionContext) =
    Future.successful(authority)

  override def logoutSucceeded(request: RequestHeader)(implicit context: ExecutionContext) =
    Future.successful(Redirect(routes.Auth.login))

  override def authenticationFailed(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] =
    Future.successful(Redirect(routes.Auth.login).withSession(accessUri -> request.uri))

  override implicit def idTag = classTag[Id]

  override def loginSucceeded(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] =
    Future.successful {
      for {
        uri <- request.session.get(accessUri)
      } println(uri)
      NotFound
    }

  def authorizationFailed(request: RequestHeader, user: User, authority: Option[Authority])(implicit context: ExecutionContext): Future[Result] =
    Future.successful(Forbidden("Not permitted."))
}
