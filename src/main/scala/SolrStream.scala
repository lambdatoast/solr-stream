package solrstream
import scalaz.stream.io
import scalaz.stream.Process
import scalaz.concurrent.Task
import scala.io.Source
import java.net.URLEncoder

object SolrStream {

  /** 
    * [[http://wiki.apache.org/solr/CommonQueryParameters CommonQueryParameters]]
    * [[http://wiki.apache.org/solr/CoreQueryParameters CoreQueryParameters]]
    */
  sealed trait QueryParameter
  final case class Q(value: String) extends QueryParameter
  final case class Wt(value: String) extends QueryParameter

  object QueryParameter {
    def kv(k: String)(v: String): String = k + "=" + URLEncoder.encode(v, "utf-8")
    def toURLQuery(qp: QueryParameter): String =
      qp match {
        case Q(v) => kv("q")(v)
        case Wt(v) => kv("wt")(v)
      }
  }
  
  /** Solr query definition.
    * [[http://wiki.apache.org/solr/QueryParametersIndex Query parameters docs]]
    */
  case class Query(params: Set[QueryParameter])

  def queryToString(q: Query): String = 
    q.params.map(QueryParameter.toURLQuery(_)).mkString("&")
  
  def query(url: String)(q: Query): Process[Task, String] =
    io.linesR(Source.fromURL(url + "?" + queryToString(q), "utf-8"))

  def example = query("http://localhost:8983/solr/collection1/select")(Query(Set(Q("*:*"), Wt("json"))))

}
