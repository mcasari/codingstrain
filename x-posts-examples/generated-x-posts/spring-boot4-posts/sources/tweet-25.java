// ❌ Boot 3 platform — too old for Boot 4
// - Servlet 6.0
// - JPA 3.1 / Validation 3.0
// - Hibernate 6.x
// - Tomcat 10 containers
// Deploying Boot 4 here fails the Servlet baseline

// ✅ Boot 4 — Jakarta EE 11 (jakarta.* names stay)
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
@Entity
public class Product {
    @Id Long id;
    @NotBlank String name;
}
// BOM: Servlet 6.1 · JPA 3.2 · Validation 3.1 · Hibernate 7.x
// Servers: Tomcat 11 / Jetty 12.1
