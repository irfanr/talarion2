package com.mascova.talarion2.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
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
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.mascova.talarion2.domain.Image;
import com.mascova.talarion2.domain.User;
import com.mascova.talarion2.repository.ImageRepository;
import com.mascova.talarion2.repository.UserRepository;
import com.mascova.talarion2.repository.specification.ImageSpecificationBuilder;
import com.mascova.talarion2.security.SecurityUtils;
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

  @Inject
  private Environment env;

  @Inject
  private UserRepository userRepository;

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

  /**
   * @throws IOException
   * 
   */
  @RequestMapping(value = "/image/file/gallery/{id}", method = RequestMethod.GET)
  @Timed
  public ResponseEntity<byte[]> getImageFileGallery(@PathVariable Long id) throws IOException {

    Image image = imageRepository.findOne(id);

    String systemPath = env.getProperty("image.host.path.system");
    String relativePath = env.getProperty("image.host.path.relative.gallery");

    if (StringUtils.equalsIgnoreCase(systemPath, "user.dir")) {
      systemPath = System.getProperty("user.dir") + "/src/main/webapp";
    }

    File imageFile = new File(systemPath + relativePath + image.getName() + "." + image.getType());

    InputStream in = null;
    try {
      in = new FileInputStream(imageFile);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, "image/" + image.getType());

    return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);

  }

  /**
   * @throws IOException
   * 
   */
  @RequestMapping(value = "/image/file/profile/{login}", method = RequestMethod.GET)
  @Timed
  public ResponseEntity<byte[]> getImageFileProfile(@PathVariable String login) throws IOException {

    File imageFile = new File(System.getProperty("user.dir") + "/src/main/webapp"
        + env.getProperty("image.host.path.relative.profile") + SecurityUtils.getCurrentUserLogin()
        + "." + "png");

    InputStream in = null;
    try {
      in = new FileInputStream(imageFile);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

    return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);

  }

  @RequestMapping(value = "/image/file/profile/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> uploadImageFileProfile(@RequestParam("file") MultipartFile file)
      throws IOException {

    User currentLoggedUser = userRepository.findOne(SecurityUtils.getCurrentUserId());

    byte[] bytes;

    if (!file.isEmpty()) {
      bytes = file.getBytes();
      // store file in storage

      try {

        System.out.println(System.getProperty("user.dir"));

        String hostUrl = env.getProperty("image.host.url");
        String systemPath = env.getProperty("image.host.path.system");
        String relativePath = env.getProperty("image.host.path.relative.profile");

        if (StringUtils.equalsIgnoreCase(systemPath, "user.dir")) {
          systemPath = System.getProperty("user.dir") + "/src/main/webapp";
        }

        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());

        File savedFile = new File(systemPath + relativePath + currentLoggedUser.getLogin() + "."
            + fileExt);

        if (savedFile.delete()) {
          // System.out.println(file.getName() + " is deleted!");
        } else {
          // System.out.println("Delete operation is failed.");
        }

        file.transferTo(savedFile);

        currentLoggedUser.setProfileImagePath(hostUrl + "/" + relativePath
            + currentLoggedUser.getLogin() + "." + fileExt);
        userRepository.save(currentLoggedUser);

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    return new ResponseEntity<>(currentLoggedUser, HttpStatus.OK);
  }

  @RequestMapping(value = "/image/file/gallery/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Image> uploadImageFileGallery(@RequestParam("file") MultipartFile file)
      throws IOException {

    Image image = new Image();

    byte[] bytes;

    if (!file.isEmpty()) {
      bytes = file.getBytes();
      // store file in storage

      try {

        System.out.println(System.getProperty("user.dir"));

        String hostUrl = env.getProperty("image.host.url");
        String systemPath = env.getProperty("image.host.path.system");
        String relativePath = env.getProperty("image.host.path.relative.gallery");

        if (StringUtils.equalsIgnoreCase(systemPath, "user.dir")) {
          systemPath = System.getProperty("user.dir") + "/src/main/webapp";
        }

        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        String baseName = liquibase.util.file.FilenameUtils.getBaseName(file.getOriginalFilename());

        File savedFile = new File(systemPath + relativePath + baseName + "." + fileExt);

        if (savedFile.delete()) {
          // System.out.println(file.getName() + " is deleted!");
        } else {
          // System.out.println("Delete operation is failed.");
        }

        file.transferTo(savedFile);

        image.setName(baseName);
        image.setType(fileExt);
        image.setUri(hostUrl + "/" + relativePath + baseName + "." + fileExt);

        imageRepository.save(image);

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    return new ResponseEntity<>(image, HttpStatus.OK);
  }
}
