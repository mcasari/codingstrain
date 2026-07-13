@GetMapping("/book/{id}")
public Book findById(@PathVariable("id") String id) {
    return bookService.findById(id);
}

@GetMapping("/book/search")
public List<Book> search(@RequestParam String title) {
    return bookService.findByTitle(title);
}
