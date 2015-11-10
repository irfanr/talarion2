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
import com.mascova.talarion2.domain.Image;
import com.mascova.talarion2.repository.ImageRepository;
import com.mascova.talarion2.repository.specification.ImageSpecificationBuilder;
import com.mascova.talarion2.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Image.
 */
@RestController
@RequestMapping("/api")
public class ImageResource {

  private final Logger log = LoggerFactory.getLogger(ImageResource.class);

  @Inject
  private ImageRepository imageRepository;

  /**
   * POST /image -> Create a new image.
   */
  @RequestMapping(value = "/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> create(@RequestBody Image image) throws URISyntaxException {
    log.debug("REST request to save Image : {}", image);
    if (image.getId() != null) {
      return ResponseEntity.badRequest().header("Failure", "A new image cannot already have an ID")
          .build();
    }
    imageRepository.save(image);
    return ResponseEntity.created(new URI("/api/image/" + image.getId())).build();
  }

  /**
   * PUT /image -> Updates an existing image.
   */
  @RequestMapping(value = "/image", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Void> update(@RequestBody Image image) throws URISyntaxException {
    log.debug("REST request to update Image : {}", image);
    if (image.getId() == null) {
      return create(image);
    }
    imageRepository.save(image);
    return ResponseEntity.ok().build();
  }

  /**
   * GET /image -> get all the images.
   */
  @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<List<Image>> getAll(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "size", required = false) Integer size,
      @RequestParam(value = "name", required = false) String name) throws URISyntaxException {

    ImageSpecificationBuilder builder = new ImageSpecificationBuilder();

    if (StringUtils.isNotBlank(name)) {
      builder.with("name2", ":", name);
    }

    Page<Image> page = imageRepository.findAll(builder.build(),
        PaginationUtil.generatePageRequest(offset, size));

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/image");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /image/:id -> get the "id" image.
   */
  @RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseEntity<Image> get(@PathVariable Long id) {
    log.debug("REST request to get Image : {}", id);
    return Optional.ofNullable(imageRepository.findOne(id))
        .map(image -> new ResponseEntity<>(image, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * DELETE /image/:id -> delete the "id" image.
   */
  @RequestMapping(value = "/image/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete Image : {}", id);
    imageRepository.delete(id);
  }
}
