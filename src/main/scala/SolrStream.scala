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

  /** [[http://wiki.apache.org/solr/CoreQueryParameters CommonQueryParameters]]
    */
  sealed trait CoreQueryParameter
  final case class Wt(value: String) extends CoreQueryParameter
  
  /** Solr query definition.
    * [[http://wiki.apache.org/solr/QueryParametersIndex Query parameters docs]]
    */
  case class Query(commonParams: Set[CommonQueryParameter], coreParams: Set[CoreQueryParameter])
  
  def query(url: String): Process[Task, String] =
    io.linesR(Source.fromURL(url, "utf-8"))

  def example = query("http://localhost:8983/solr/collection1/select?q=*%3A*&wt=json&indent=true")

}
