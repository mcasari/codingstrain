package com.codingstrain.springcloud.sample.libraryapp.borrowers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.codingstrain.springcloud.sample.libraryapp.borrowers.model.Borrower;



@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, String>, JpaSpecificationExecutor<Borrower> {
	List<Borrower> findByAuthor(String author);
}
