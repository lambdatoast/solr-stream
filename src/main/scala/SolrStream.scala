package solrstream
import scalaz.stream.io
import scalaz.stream.Process
import scalaz.concurrent.Task
import scala.io.Source

object SolrStream {

  /** [[http://wiki.apache.org/solr/CommonQueryParameters CommonQueryParameters]]
    */
  sealed trait CommonQueryParameter
  final case class Q(value: String) extends CommonQueryParameter

  object CommonQueryParam {
    def toURLQuery(qp: CommonQueryParameter): String =
      qp match {
        case Q(v) => "q" + "=" + v
      }
  }

  /** [[http://wiki.apache.org/solr/CoreQueryParameters CommonQueryParameters]]
    */
  sealed trait CoreQueryParameter
  final case class Wt(value: String) extends CoreQueryParameter

  object CoreQueryParam {
    def toURLQuery(qp: CoreQueryParameter): String =
      qp match {
        case Wt(v) => "wt" + "=" + v
      }
  }
  
  /** Solr query definition.
    * [[http://wiki.apache.org/solr/QueryParametersIndex Query parameters docs]]
    */
  case class Query(commonParams: Set[CommonQueryParameter], coreParams: Set[CoreQueryParameter])

  def queryToString(q: Query): String = 
    q.commonParams.map(CommonQueryParam.toURLQuery(_)).mkString("&") + 
    "&" + q.coreParams.map(CoreQueryParam.toURLQuery(_)).mkString("&")
  
  def query(url: String)(q: Query): Process[Task, String] =
    io.linesR(Source.fromURL(url + "?" + queryToString(q), "utf-8"))

  def example = query("http://localhost:8983/solr/collection1/select")(Query(Set(Q("*:*")), Set(Wt("json"))))

}
