package com.mascova.talarion2.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mascova.talarion2.domain.District;
import com.mascova.talarion2.domain.SubDistrict;
import com.mascova.talarion2.repository.DistrictRepository;
import com.mascova.talarion2.repository.SubDistrictRepository;
import com.mascova.talarion2.repository.specification.SubDistrictSpecificationBuilder;
import com.mascova.talarion2.web.rest.util.PaginationUtil;

/**
 * REST controller for managing SubDistrict.
 */
@RestController
@RequestMapping("/api")
public class SubDistrictResource {

  private final Logger log = LoggerFactory.getLogger(SubDistrictResource.class);

  @Inject
  private DistrictRepository districtRepository;

  @Inject
  private SubDistrictRepository subDistrictRepository;

  /**
   * POST /subDistrict -> Create a new subDistrict.
   */
  @RequestMapping(value = "/subDistrict", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody SubDistrict subDistrict)
      throws URISyntaxException {
    log.debug("REST request to save SubDistrict : {}", subDistrict);
    if (subDistrict.getId() != null) {
      return ResponseEntity.badRequest()
          .header("Failure", "A new subDistrict cannot already have an ID").build();
    }
    District persistedDistrict = districtRepository.findOne(subDistrict.getDistrict().getId());
    subDistrict.setDistrict(persistedDistrict);
    subDistrictRepository.save(subDistrict);
    return ResponseEntity.created(new URI("/api/subDistrict/" + subDistrict.getId())).build();
  }

  /**
   * PUT /subDistrict -> Updates an existing subDistrict.
   */
  @RequestMapping(value = "/subDistrict", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> update(@RequestBody SubDistrict subDistrict)
      throws URISyntaxException {
    log.debug("REST request to update SubDistrict : {}", subDistrict);
    if (subDistrict.getId() == null) {
      return create(subDistrict);
    }

    SubDistrict persistedSubDistrict = subDistrictRepository.findOne(subDistrict.getId());
    BeanUtils.copyProperties(subDistrict, persistedSubDistrict, "district");

    if (persistedSubDistrict.getDistrict().getId().longValue() != subDistrict.getDistrict().getId()
        .longValue()) {
      District persistedDistrict = districtRepository.findOne(subDistrict.getDistrict().getId());
      persistedSubDistrict.setDistrict(persistedDistrict);
    }
    subDistrictRepository.save(persistedSubDistrict);
    return ResponseEntity.ok().build();
  }

  /**
   * GET /subDistrict -> get all the subDistricts.
   */
  @RequestMapping(value = "/subDistrict", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<SubDistrict>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit,
      @RequestParam(value = "name", required = false) String name) throws URISyntaxException {

    SubDistrictSpecificationBuilder builder = new SubDistrictSpecificationBuilder();

    if (StringUtils.isNotBlank(name)) {
      builder.with("name", ":", name);
    }

    Page<SubDistrict> page = subDistrictRepository.findAll(builder.build(),
        PaginationUtil.generatePageRequest(offset, limit));

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subDistrict");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /subDistrict/:id -> get the "id" subDistrict.
   */
  @RequestMapping(value = "/subDistrict/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<SubDistrict> get(@PathVariable Long id) {
    log.debug("REST request to get SubDistrict : {}", id);
    return Optional.ofNullable(subDistrictRepository.findOne(id))
        .map(subDistrict -> new ResponseEntity<>(subDistrict, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /subDistrict/:id -> delete the "id" subDistrict.
   */
  @RequestMapping(value = "/subDistrict/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete SubDistrict : {}", id);
    subDistrictRepository.delete(id);
  }
}
