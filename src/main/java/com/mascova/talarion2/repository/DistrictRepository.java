package com.mascova.talarion2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mascova.talarion2.domain.District;

/**
 * Spring Data JPA repository for the Author entity.
 */
public interface DistrictRepository extends JpaRepository<District, Long>,
    JpaSpecificationExecutor<District> {

}
