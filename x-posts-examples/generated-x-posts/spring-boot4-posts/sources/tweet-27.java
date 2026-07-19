# application.yml
spring:
  elasticsearch:
    uris: https://search.example.com:9200
    api-key: ${ELASTIC_API_KEY}

// Inject ElasticsearchClient / RestClient as usual
@Service
public class ProductSearch {
    private final ElasticsearchClient client;

    public ProductSearch(ElasticsearchClient client) {
        this.client = client;
    }
}
