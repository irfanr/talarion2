package com.mascova.talarion2.web.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing Category.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

  private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public void upload(@RequestParam("file") MultipartFile file,
      @RequestParam("username") String username) throws IOException {

    byte[] bytes;

    if (!file.isEmpty()) {
      bytes = file.getBytes();
      // store file in storage
    }

    System.out.println(String.format("receive %s from %s", file.getOriginalFilename(), username));
  }

}
