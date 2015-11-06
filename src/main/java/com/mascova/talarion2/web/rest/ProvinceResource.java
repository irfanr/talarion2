package com.mascova.talarion2.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.mascova.talarion2.domain.Province;
import com.mascova.talarion2.repository.ProvinceRepository;
import com.mascova.talarion2.repository.specification.ProvinceSpecificationBuilder;
import com.mascova.talarion2.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Province.
 */
@RestController
@RequestMapping("/api")
public class ProvinceResource {

  private final Logger log = LoggerFactory.getLogger(ProvinceResource.class);

  @Inject
  private ProvinceRepository provinceRepository;

  /**
   * POST /province -> Create a new province.
   */
  @RequestMapping(value = "/province", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody Province province) throws URISyntaxException {
    log.debug("REST request to save Province : {}", province);
    if (province.getId() != null) {
      return ResponseEntity.badRequest()
          .header("Failure", "A new province cannot already have an ID").build();
    }
    provinceRepository.save(province);
    return ResponseEntity.created(new URI("/api/province/" + province.getId())).build();
  }

  /**
   * PUT /province -> Updates an existing province.
   */
  @RequestMapping(value = "/province", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> update(@RequestBody Province province) throws URISyntaxException {
    log.debug("REST request to update Province : {}", province);
    if (province.getId() == null) {
      return create(province);
    }
    provinceRepository.save(province);
    return ResponseEntity.ok().build();
  }

  /**
   * GET /province -> get all the provinces.
   */
  @RequestMapping(value = "/province", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<Province>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit,
      @RequestParam(value = "name", required = false) String name) throws URISyntaxException {

    ProvinceSpecificationBuilder builder = new ProvinceSpecificationBuilder();

    if (StringUtils.isNotBlank(name)) {
      builder.with("name", ":", name);
    }

    Page<Province> page = provinceRepository.findAll(builder.build(),
        PaginationUtil.generatePageRequest(offset, limit));

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/province");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /province/:id -> get the "id" province.
   */
  @RequestMapping(value = "/province/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Province> get(@PathVariable Long id) {
    log.debug("REST request to get Province : {}", id);
    return Optional.ofNullable(provinceRepository.findOne(id))
        .map(province -> new ResponseEntity<>(province, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /province/:id -> delete the "id" province.
   */
  @RequestMapping(value = "/province/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete Province : {}", id);
    provinceRepository.delete(id);
  }
}
