@Validated
@ConfigurationProperties(prefix = "app")
public class AppProperties {
  @NotBlank private String name;
  @Min(1) @Max(60) private Integer timeout;
}
