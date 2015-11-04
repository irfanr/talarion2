package com.mascova.talarion2.repository;

import com.mascova.talarion2.domain.PersistentToken;
import com.mascova.talarion2.domain.User;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
