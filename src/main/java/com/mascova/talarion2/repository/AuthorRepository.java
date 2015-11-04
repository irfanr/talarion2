package com.mascova.talarion2.repository;

import com.mascova.talarion2.domain.Author;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Author entity.
 */
public interface AuthorRepository extends JpaRepository<Author,Long> {

}
