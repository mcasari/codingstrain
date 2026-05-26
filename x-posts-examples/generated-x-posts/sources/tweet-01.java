@RestController
@RequestMapping("/library")
public class BookController {
  @GetMapping("/book")
  public List<Book> findAll() { ... }
}
