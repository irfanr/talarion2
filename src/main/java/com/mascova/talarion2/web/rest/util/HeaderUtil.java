package com.mascova.talarion2.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for http header creation.
 *
 */
public class HeaderUtil {

  public static HttpHeaders createAlert(String message, String param) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-talarion2App-alert", message);
    headers.add("X-talarion2App-params", param);
    return headers;
  }

  public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
    return createAlert("talarion2App." + entityName + ".created", param);
  }

  public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
    return createAlert("talarion2App." + entityName + ".updated", param);
  }

  public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
    return createAlert("talarion2App." + entityName + ".deleted", param);
  }

  public static HttpHeaders createFailureAlert(String entityName, String errorKey,
      String defaultMessage) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-talarion2App-error", "error." + errorKey);
    headers.add("X-talarion2App-params", entityName);
    return headers;
  }

}
