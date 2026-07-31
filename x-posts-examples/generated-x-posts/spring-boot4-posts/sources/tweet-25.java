// jakarta.* imports stay — but versions moved up
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.servlet.http.HttpServletRequest;

@Entity
public class Product {
    @Id Long id;
    @NotBlank String name;
}

// Boot parent manages:
// jakarta.servlet-api 6.1, jakarta.persistence 3.2,
// hibernate 7.x, validation 3.1
