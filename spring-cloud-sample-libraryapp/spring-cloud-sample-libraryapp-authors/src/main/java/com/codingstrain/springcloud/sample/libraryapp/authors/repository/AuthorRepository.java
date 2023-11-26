package com.codingstrain.springcloud.sample.libraryapp.authors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.codingstrain.springcloud.sample.libraryapp.authors.model.Author;



@Repository
public interface AuthorRepository extends JpaRepository<Author, String>, JpaSpecificationExecutor<Author> {
	List<Author> findAllByFirstNameAndLastName(String firstName, String lastName);
}
