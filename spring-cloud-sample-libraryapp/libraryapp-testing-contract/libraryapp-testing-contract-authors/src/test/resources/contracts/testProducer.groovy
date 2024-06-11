import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "return biography info for an author named Goethe"
    request {
        method GET()
        url("/authors/findAuthor") {
            queryParameters {
                parameter("name", "Goethe")
            }
        }
    }
    response {
        body("{\"name\":\"Goethe\",\"biography\":\"Bio info\"}")
        status 200
    }
}