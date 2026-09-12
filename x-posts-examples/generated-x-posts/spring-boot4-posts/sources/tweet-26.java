# ❌ URI basic auth — secrets in the connection string
spring:
  elasticsearch:
    uris: https://user:pass@search.example.com:9200
# Hard to rotate without rewriting the URI

# ✅ Boot 4 — spring.elasticsearch.api-key
spring:
  elasticsearch:
    uris: https://search.example.com:9200
    api-key: ${ELASTIC_API_KEY}
// Client wiring unchanged
@Service
class ProductSearch {
    private final ElasticsearchClient client;
    ProductSearch(ElasticsearchClient client) {
        this.client = client;
    }
}
