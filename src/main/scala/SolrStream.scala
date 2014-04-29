package solrstream

object SolrStream {
  import Query._

  def example = query("http://localhost:8983/solr/collection1/select")(Query(Set(Q("*:*"), Wt("json"))))

}
