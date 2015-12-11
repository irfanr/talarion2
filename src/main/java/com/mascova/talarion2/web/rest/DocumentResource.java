package com.mascova.talarion2.web.rest;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
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

import com.mascova.talarion2.domain.Document;
import com.mascova.talarion2.repository.DocumentRepository;
import com.mascova.talarion2.repository.UserRepository;

/**
 * REST controller for managing Image.
 */
@RestController
@RequestMapping("/api")
public class DocumentResource {

  private final Logger log = LoggerFactory.getLogger(DocumentResource.class);

  @Inject
  private DocumentRepository documentRepository;

  @Inject
  private Environment env;

  @Inject
  private UserRepository userRepository;

  @RequestMapping(value = "/file/document/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Document> uploadFileDocument(@RequestParam("file") MultipartFile file)
      throws IOException {

    Document document = new Document();

    byte[] bytes;

    if (!file.isEmpty()) {
      bytes = file.getBytes();
      // store file in storage

      try {

        System.out.println(System.getProperty("user.dir"));

        String hostUrl = env.getProperty("file.host.url");
        String systemPath = env.getProperty("file.host.path.system");
        String relativePath = env.getProperty("file.host.path.relative.document");

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

        document.setName(baseName);
        document.setType(fileExt);
        document.setUri(hostUrl + "/" + relativePath + baseName + "." + fileExt);

        documentRepository.save(document);

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    return new ResponseEntity<>(document, HttpStatus.OK);
  }

}
