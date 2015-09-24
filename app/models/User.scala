package models

/**
 * Created by kazzna on 15/09/23.
 */
case class User(id: String, password: String)

object User {
  def selectById(id: String): Option[User] = if (id == "kazzna") {
    Some(User("kazzna", "password"))
  } else None

  def authenticate(id: String, password: String) = (id, password) match {
    case ("kazzna", "password") => Some(User(id, password))
    case _ => None
  }
}
