package solrstream
import java.net.URLEncoder

/** 
  * [[http://wiki.apache.org/solr/CommonQueryParameters CommonQueryParameters]]
  * [[http://wiki.apache.org/solr/CoreQueryParameters CoreQueryParameters]]
  */
sealed trait QueryParameter
final case class Q(value: String) extends QueryParameter
final case class Wt(value: String) extends QueryParameter
final case class Fl(value: Set[String]) extends QueryParameter

object QueryParameter {
  def kv(k: String)(v: String): String = k + "=" + URLEncoder.encode(v, "utf-8")
  def toURLQuery(qp: QueryParameter): String =
    qp match {
      case Q(v) => kv("q")(v)
      case Wt(v) => kv("wt")(v)
      case Fl(vs) => kv("fl")(vs.mkString(","))
    }
}