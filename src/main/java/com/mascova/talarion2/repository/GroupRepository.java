package com.mascova.talarion2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascova.talarion2.domain.Group;

/**
 * Spring Data JPA repository for the Group entity.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
}
