package clabot

import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write
import scalaj.http.{Http, HttpResponse}

case class Github (token: String) {

  implicit val formats = DefaultFormats

  val GITHUB_URI = "https://api.github.com"

  def postMessage(owner: String, repo: String, issue: String, message: String): HttpResponse[String] = {
    Http(s"$GITHUB_URI/repos/$owner/$repo/issues/$issue/comments")
      .header("Authorization", s"token $token")
      .postData(write(PostMessageRequest(message)))
      .asString
  }

  def addLabel(owner: String, repo: String, issue: String, label: String): HttpResponse[String] = {
    Http(s"$GITHUB_URI/repos/$owner/$repo/issues/$issue/labels")
      .header("Authorization", s"token $token")
      .postData(write(List(label)))
      .asString
  }

  def deleteLabel(owner: String, repo: String, issue: String, label: String): HttpResponse[String] = {
    Http(s"$GITHUB_URI/repos/$owner/$repo/issues/$issue/labels/$label")
      .header("Authorization", s"token $token")
      .method("DELETE")
      .asString
  }

  def listMembers(org: String): HttpResponse[String] = {
    Http(s"$GITHUB_URI/orgs/$org/members")
      .header("Authorization", s"token $token")
      .asString
  }

}

case class PostMessageRequest(body: String)