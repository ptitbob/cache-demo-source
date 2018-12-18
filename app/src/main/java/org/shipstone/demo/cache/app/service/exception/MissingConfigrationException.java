package org.shipstone.demo.cache.app.service.exception;

import org.shipstone.demo.cache.commons.web.exceptions.RegisteredException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */public class MissingConfigrationException extends RegisteredException {

  private final String key;

  public MissingConfigrationException(String code, String key) {
    super(INTERNAL_SERVER_ERROR, code, String.format("Manque configuration : %s", key));
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
