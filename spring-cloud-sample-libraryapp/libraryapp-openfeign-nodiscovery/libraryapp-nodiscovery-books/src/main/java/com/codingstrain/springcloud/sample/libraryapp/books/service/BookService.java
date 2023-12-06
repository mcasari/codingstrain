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

    public BookInfo findBookInfoByRestTemplate(String urlAuthorInfo, String urlBookReviewInfo, String authorName, String bookTitle) throws BookException {
        ResponseEntity<String> responseAuthorInfo = restTemplate.getForEntity(urlAuthorInfo + "/" + authorName, String.class);
        BookInfo bookInfo = new BookInfo();
        bookInfo = parseAuthorInfo(bookInfo, responseAuthorInfo);
        ResponseEntity<String> response2 = restTemplate.getForEntity(urlBookReviewInfo + "/" + bookTitle, String.class);
        bookInfo = parseReviewInfo(bookInfo, response2);
        return bookInfo;
    }

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findById(title);
    }

    private BookInfo parseAuthorInfo(BookInfo bookInfo, ResponseEntity<String> response) throws BookException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
            String authorName = "";
            String authorBiography = "";
            JsonNode node = root.path("name");
            authorName = node != null ? node.asText() : "";
            node = root.path("biography");
            authorBiography = node != null ? node.asText() : "";
            bookInfo.setAuthorName(authorName);
            bookInfo.setAuthorBiography(authorBiography);
        } catch (Exception e) {
            throw new BookException("Error in parsing JSON author info!", e);
        }
        return null;
    }

    private BookInfo parseReviewInfo(BookInfo bookInfo, ResponseEntity<String> response) throws BookException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
            String authorName = "";
            String authorBiography = "";
            JsonNode node = root.path("name");
            authorName = node != null ? node.asText() : "";
            node = root.path("biography");
            authorBiography = node != null ? node.asText() : "";
            bookInfo.setAuthorName(authorName);
            bookInfo.setAuthorBiography(authorBiography);
        } catch (Exception e) {
            throw new BookException("Error in parsing JSON reviews info!", e);
        }
        return null;
    }
}
