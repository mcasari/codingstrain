package com.codingstrain.springcloud.sample.libraryapp.books.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codingstrain.springcloud.sample.libraryapp.books.BookException;
import com.codingstrain.springcloud.sample.libraryapp.books.dto.BookInfo;
import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.repository.BookRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("bookService")
public class BookService {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate;

    public BookInfo findBookInfoByRestTemplate(String urlAuthorInfo, String urlBookReviewInfo, String title) throws BookException {
        ResponseEntity<String> response1 = restTemplate.getForEntity(urlAuthorInfo + "/" + title, String.class);
        BookInfo bookInfo1 = parseAuthorInfo(response1);
        ResponseEntity<String> response2 = restTemplate.getForEntity(urlBookReviewInfo + "/" + title, String.class);
        BookInfo bookInfo2 = parseAuthorInfo(response2);
        return bookInfo1;
    }

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findById(title);
    }

    private BookInfo parseAuthorInfo(ResponseEntity<String> response) throws BookException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
            JsonNode name = root.path("name");
        } catch (Exception e) {
            throw new BookException(e);
        }
        return null;
    }

}
