<!-- ❌ No docs — clients guess your API from code alone -->
<!-- (no springdoc dependency, no /swagger-ui.html) -->

<!-- ✅ springdoc-openapi — OpenAPI 3 from REST controllers -->
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>
@RestController
@RequestMapping("/users")
class UserController {
    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    User get(@PathVariable Long id) {
        return users.findById(id);
    }
}
// UI: /swagger-ui.html   JSON: /v3/api-docs
