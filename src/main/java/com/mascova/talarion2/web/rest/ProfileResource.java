package com.mascova.talarion2.web.rest;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mascova.talarion2.domain.User;
import com.mascova.talarion2.repository.UserRepository;
import com.mascova.talarion2.security.SecurityUtils;

/**
 * REST controller for managing Category.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

  private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

  @Inject
  private Environment env;

  @Inject
  private UserRepository userRepository;

  @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> upload(@RequestParam("file") MultipartFile file,
      @RequestParam("username") String username) throws IOException {

    User currentLoggedUser = userRepository.findOne(SecurityUtils.getCurrentUserId());

    byte[] bytes;

    if (!file.isEmpty()) {
      bytes = file.getBytes();
      // store file in storage

      try {

        System.out.println(System.getProperty("user.dir"));

        String hostUrl = env.getProperty("image.host.url");
        String systemPath = env.getProperty("image.host.path.system");
        String relativePath = env.getProperty("image.host.path.relative");

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

    System.out.println(String.format("receive %s from %s", file.getOriginalFilename(), username));

    return new ResponseEntity<>(currentLoggedUser, HttpStatus.OK);
  }
}
