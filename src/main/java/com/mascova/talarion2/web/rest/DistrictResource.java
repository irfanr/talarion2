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
import com.mascova.talarion2.domain.Province;
import com.mascova.talarion2.repository.DistrictRepository;
import com.mascova.talarion2.repository.ProvinceRepository;
import com.mascova.talarion2.repository.specification.DistrictSpecificationBuilder;
import com.mascova.talarion2.web.rest.util.PaginationUtil;

/**
 * REST controller for managing District.
 */
@RestController
@RequestMapping("/api")
public class DistrictResource {

  private final Logger log = LoggerFactory.getLogger(DistrictResource.class);

  @Inject
  private ProvinceRepository provinceRepository;

  @Inject
  private DistrictRepository districtRepository;

  /**
   * POST /district -> Create a new district.
   */
  @RequestMapping(value = "/district", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody District district) throws URISyntaxException {
    log.debug("REST request to save District : {}", district);
    if (district.getId() != null) {
      return ResponseEntity.badRequest()
          .header("Failure", "A new district cannot already have an ID").build();
    }
    Province persistedProvince = provinceRepository.findOne(district.getProvince().getId());
    district.setProvince(persistedProvince);
    districtRepository.save(district);
    return ResponseEntity.created(new URI("/api/district/" + district.getId())).build();
  }

  /**
   * PUT /district -> Updates an existing district.
   */
  @RequestMapping(value = "/district", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> update(@RequestBody District district) throws URISyntaxException {
    log.debug("REST request to update District : {}", district);
    if (district.getId() == null) {
      return create(district);
    }

    District persistedDistrict = districtRepository.findOne(district.getId());
    BeanUtils.copyProperties(district, persistedDistrict, "province");

    if (persistedDistrict.getProvince().getId().longValue() != district.getProvince().getId()
        .longValue()) {
      Province persistedProvince = provinceRepository.findOne(district.getProvince().getId());
      persistedDistrict.setProvince(persistedProvince);
    }

    districtRepository.save(persistedDistrict);
    return ResponseEntity.ok().build();
  }

  /**
   * GET /district -> get all the districts.
   */
  @RequestMapping(value = "/district", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<District>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit,
      @RequestParam(value = "name", required = false) String name) throws URISyntaxException {

    DistrictSpecificationBuilder builder = new DistrictSpecificationBuilder();

    if (StringUtils.isNotBlank(name)) {
      builder.with("name", ":", name);
    }

    Page<District> page = districtRepository.findAll(builder.build(),
        PaginationUtil.generatePageRequest(offset, limit));

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/district");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /district/:id -> get the "id" district.
   */
  @RequestMapping(value = "/district/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<District> get(@PathVariable Long id) {
    log.debug("REST request to get District : {}", id);
    return Optional.ofNullable(districtRepository.findOne(id))
        .map(district -> new ResponseEntity<>(district, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /district/:id -> delete the "id" district.
   */
  @RequestMapping(value = "/district/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete District : {}", id);
    districtRepository.delete(id);
  }
}
