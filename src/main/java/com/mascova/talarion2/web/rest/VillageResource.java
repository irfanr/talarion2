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
import com.mascova.talarion2.domain.SubDistrict;
import com.mascova.talarion2.domain.Village;
import com.mascova.talarion2.repository.SubDistrictRepository;
import com.mascova.talarion2.repository.VillageRepository;
import com.mascova.talarion2.repository.specification.VillageSpecificationBuilder;
import com.mascova.talarion2.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Village.
 */
@RestController
@RequestMapping("/api")
public class VillageResource {

  private final Logger log = LoggerFactory.getLogger(VillageResource.class);

  @Inject
  private SubDistrictRepository subDistrictRepository;

  @Inject
  private VillageRepository villageRepository;

  /**
   * POST /village -> Create a new village.
   */
  @RequestMapping(value = "/village", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody Village village) throws URISyntaxException {
    log.debug("REST request to save Village : {}", village);
    if (village.getId() != null) {
      return ResponseEntity.badRequest()
          .header("Failure", "A new village cannot already have an ID").build();
    }
    SubDistrict persistedSubDistrict = subDistrictRepository.findOne(village.getSubDistrict()
        .getId());
    village.setSubDistrict(persistedSubDistrict);
    villageRepository.save(village);
    return ResponseEntity.created(new URI("/api/village/" + village.getId())).build();
  }

  /**
   * PUT /village -> Updates an existing village.
   */
  @RequestMapping(value = "/village", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> update(@RequestBody Village village) throws URISyntaxException {
    log.debug("REST request to update Village : {}", village);
    if (village.getId() == null) {
      return create(village);
    }

    Village persistedVillage = villageRepository.findOne(village.getId());
    BeanUtils.copyProperties(village, persistedVillage, "subDistrict");

    if (persistedVillage.getSubDistrict().getId().longValue() != village.getSubDistrict().getId()
        .longValue()) {
      SubDistrict persistedSubDistrict = subDistrictRepository.findOne(village.getSubDistrict()
          .getId());
      persistedVillage.setSubDistrict(persistedSubDistrict);
    }

    villageRepository.save(persistedVillage);
    return ResponseEntity.ok().build();
  }

  /**
   * GET /village -> get all the villages.
   */
  @RequestMapping(value = "/village", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<Village>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "per_page", required = false) Integer limit,
      @RequestParam(value = "name", required = false) String name) throws URISyntaxException {

    VillageSpecificationBuilder builder = new VillageSpecificationBuilder();

    if (StringUtils.isNotBlank(name)) {
      builder.with("name", ":", name);
    }

    Page<Village> page = villageRepository.findAll(builder.build(),
        PaginationUtil.generatePageRequest(offset, limit));

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/village");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /village/:id -> get the "id" village.
   */
  @RequestMapping(value = "/village/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Village> get(@PathVariable Long id) {
    log.debug("REST request to get Village : {}", id);
    return Optional.ofNullable(villageRepository.findOne(id))
        .map(village -> new ResponseEntity<>(village, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /village/:id -> delete the "id" village.
   */
  @RequestMapping(value = "/village/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete Village : {}", id);
    villageRepository.delete(id);
  }
}
