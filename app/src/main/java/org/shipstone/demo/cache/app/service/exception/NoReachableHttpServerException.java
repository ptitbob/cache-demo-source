package org.shipstone.demo.cache.app.service.exception;

import org.shipstone.demo.cache.commons.web.exceptions.RegisteredException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */public class NoReachableHttpServerException extends RegisteredException {

  private final String url;

  public NoReachableHttpServerException(String code, String url) {
    super(INTERNAL_SERVER_ERROR, code, String.format("Impossible d'atteindre l'url : %s", url));
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

}
