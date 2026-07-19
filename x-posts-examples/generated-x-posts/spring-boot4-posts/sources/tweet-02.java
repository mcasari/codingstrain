# application.yml
spring:
  mvc:
    apiversion:
      required: true
      default: "1.0"
      use:
        header: X-API-Version

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping(version = "1.0")
    public OrderV1 getV1(@PathVariable Long id) {
        return orderService.findV1(id);
    }

    @GetMapping(version = "2.0")
    public OrderV2 getV2(@PathVariable Long id) {
        return orderService.findV2(id);
    }
}
