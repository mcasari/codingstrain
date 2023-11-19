package com.codingstrain.springcloud.sample.libraryapp.lending.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.codingstrain.springcloud.sample.libraryapp.lending.model.Lending;



@Repository
public interface LendingRepository extends JpaRepository<Lending, String>, JpaSpecificationExecutor<Lending> {
	List<Lending> findByAuthor(String author);
}
