package solrstream

import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http._
import scalaz.stream.Process
import scalaz.stream.io
import scalaz.concurrent.Task

object Update {

  def post(url: String)(format: String)(content: String): HttpPost = {
    val post = new HttpPost(url)
    post.setHeader("Content-type", format)
    post.setEntity(new StringEntity(content))
    post
  }

  /** [[http://wiki.apache.org/solr/UpdateJSON UpdateJSON]]
    */
  def update(url: String)(format: String)(content: String): Process[Task, String] = {
    val response: HttpResponse = HttpClientBuilder.create().build().execute(post(url)(format)(content))
    io.linesR(response.getEntity().getContent())
  }

}
