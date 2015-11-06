package com.mascova.talarion2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mascova.talarion2.domain.SubDistrict;

/**
 * Spring Data JPA repository for the Author entity.
 */
public interface SubDistrictRepository extends JpaRepository<SubDistrict, Long>,
    JpaSpecificationExecutor<SubDistrict> {

}
