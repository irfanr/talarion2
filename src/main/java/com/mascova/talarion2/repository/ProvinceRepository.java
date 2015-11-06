package com.mascova.talarion2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mascova.talarion2.domain.Province;

/**
 * Spring Data JPA repository for the Author entity.
 */
public interface ProvinceRepository extends JpaRepository<Province, Long>,
    JpaSpecificationExecutor<Province> {

}
