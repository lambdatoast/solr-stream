package solrstream
import scalaz.stream.io
import scalaz.stream.Process
import scalaz.concurrent.Task
import scala.io.Source

/** Solr query definition.
  * [[http://wiki.apache.org/solr/QueryParametersIndex Query parameters docs]]
  */
case class Query(params: List[QueryParameter])

object Query {
  def queryToString(q: Query): String = 
    q.params.map(QueryParameter.toURLQuery(_)).mkString("&")

  def query(url: String)(q: Query): Process[Task, String] =
    io.linesR(Source.fromURL(url + "?" + queryToString(q), "utf-8"))
}
