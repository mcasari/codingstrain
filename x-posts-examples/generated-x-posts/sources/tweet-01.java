@RestController
@RequestMapping("/library")
public class BookController {

    @GetMapping("/book")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @PostMapping("/book")
    public Book add(@RequestBody Book book) {
        return bookService.save(book);
    }
}
