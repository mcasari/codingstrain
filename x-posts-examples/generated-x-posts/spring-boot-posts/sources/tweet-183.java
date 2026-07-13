// @RestController = @Controller + @ResponseBody
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/users")
    public List<User> all() {
        return service.findAll();   // serialized to JSON
    }
}
