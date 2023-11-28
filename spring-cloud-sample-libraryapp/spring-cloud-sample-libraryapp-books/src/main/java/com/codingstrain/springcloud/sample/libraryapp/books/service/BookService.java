package com.codingstrain.springcloud.sample.libraryapp.books.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codingstrain.springcloud.sample.libraryapp.books.dto.BookInfo;
import com.codingstrain.springcloud.sample.libraryapp.books.model.Book;
import com.codingstrain.springcloud.sample.libraryapp.books.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("bookService")
public class BookService {

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findById(title);
    }

    public BookInfo getBookInfoByRestTemplate(String isbn) throws JsonMappingException, JsonProcessingException {
        BookInfo bookInfo = new BookInfo();
        //Get author biography info
        String fooResourceUrl = "http://localhost:8080/library/author";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode name = root.path("name");
        return bookInfo;
    }

}
