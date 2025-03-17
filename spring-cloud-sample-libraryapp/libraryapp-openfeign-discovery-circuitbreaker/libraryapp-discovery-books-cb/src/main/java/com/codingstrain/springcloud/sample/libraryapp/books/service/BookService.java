package com.codingstrain.springcloud.sample.libraryapp.books.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingstrain.springcloud.sample.libraryapp.books.client.AuthorClient;
import com.codingstrain.springcloud.sample.libraryapp.books.client.ReviewClient;
import com.codingstrain.springcloud.sample.libraryapp.books.dto.BookInfo;
import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.repository.BookRepository;
import com.codingstrain.springcloud.sample.libraryapp.model.entity.Author;
import com.codingstrain.springcloud.sample.libraryapp.model.entity.Review;

@Service("bookService")
public class BookService {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private AuthorClient authorClient;

    @Autowired
    private ReviewClient reviewClient;

    @Autowired
    private BookRepository bookRepository;

    public BookInfo findBookInfoByTitle(@RequestParam("authorName") String authorName, @RequestParam("bookTitle") String bookTitle) {
        Optional<Author> author = authorClient.findByName(authorName);
        List<Review> reviews = reviewClient.findByBookTitle(bookTitle);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setAuthorBiography(author.get()
            .getBiography());
        bookInfo.setAuthorName(authorName);
        List<String> reviewContents = reviews.stream()
            .map(item -> item.getContent())
            .collect(Collectors.toList());
        bookInfo.setTitle(bookTitle);
        bookInfo.setBookReviews(reviewContents);
        return bookInfo;
    }

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }


    public String getAuthorServiceInstance() {
        return authorClient.getInstance();
    }

    public String getAuthorServiceInstanceLB() {
        return authorClient.getInstanceLB();
    }

}
