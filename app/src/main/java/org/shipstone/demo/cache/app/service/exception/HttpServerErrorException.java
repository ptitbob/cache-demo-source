package org.shipstone.demo.cache.app.service.exception;

import org.shipstone.demo.cache.commons.web.exceptions.RegisteredException;
import org.springframework.web.client.HttpStatusCodeException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */public class HttpServerErrorException extends RegisteredException {

  private final String url;

  public HttpServerErrorException(String code, String url, HttpStatusCodeException e) {
    super(INTERNAL_SERVER_ERROR, code, String.format("Erreur [%s] lors de l'appel de l'url : %s ", e.getStatusCode().toString(), url));
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

}
