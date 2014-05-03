package solrstream

object SolrStream {
  import Query._

  object Examples {
    def search = query("http://localhost:8983/solr/collection1/select")(Query(List(Q("*:*"), Wt("json"))))
    def update = {
      val testDocsJson = 
        """
        [
         {"id" : "TestDoc1", "title" : "test1"},
         {"id" : "TestDoc2", "title" : "another test"}
        ]
        """;
      Update.update("http://localhost:8983/solr/update/json?commit=true")("application/json")(testDocsJson)
    }
  }

}
