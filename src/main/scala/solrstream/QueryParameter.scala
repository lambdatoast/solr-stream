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
final case object Facet extends QueryParameter
final case class FacetField(value: String) extends QueryParameter

object QueryParameter {
  def kv(k: String)(v: String): String = k + "=" + URLEncoder.encode(v, "utf-8")
  def toURLQuery(qp: QueryParameter): String =
    qp match {
      case Q(v) => kv("q")(v)
      case Wt(v) => kv("wt")(v)
      case Fl(vs) => kv("fl")(vs.mkString(","))
      case Facet => kv("facet")("true")
      case FacetField(v) => kv("facet.field")(v)
    }
}

/**
 * [[http://lucene.apache.org/core/2_9_4/queryparsersyntax.html queryparsersyntax]]
 */
object QueryParameterCombinators {
  type FieldTermPair = (String, String)
  def joinWithOp(xs: Set[FieldTermPair])(op: String): String =
    xs.map(kv => kv._1 + ":" + kv._2).mkString(" " + op + " ")

  def and(xs: Set[FieldTermPair]): String =
    joinWithOp(xs)("AND")

  def or(xs: Set[FieldTermPair]): String =
    joinWithOp(xs)("OR")
}
