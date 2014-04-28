package solrstream
import scalaz.stream.io
import scalaz.stream.Process
import scalaz.concurrent.Task
import scala.io.Source

object SolrStream {
  
  def query(url: String): Process[Task, String] =
    io.linesR(Source.fromURL(url, "utf-8"))

  def example = query("http://localhost:8983/solr/collection1/select?q=*%3A*&wt=json&indent=true")

}
