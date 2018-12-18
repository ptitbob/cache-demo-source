package org.shipstone.demo.cache.app.web.exception.wrapper;

import org.shipstone.demo.cache.app.service.exception.MissingConfigrationException;
import org.shipstone.demo.cache.app.service.exception.NoReachableHttpServerException;
import org.shipstone.demo.cache.app.service.exception.ZipcodeTooManyCitiesException;
import org.shipstone.demo.cache.commons.web.exceptions.ExceptionWrappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */@ControllerAdvice
public class Wrappers extends ExceptionWrappers {

  @ExceptionHandler(NoReachableHttpServerException.class)
  public ResponseEntity<String> noReachableHttpTargetException(
      NoReachableHttpServerException e
  ) {
    return responseEntity(e);
  }

  @ExceptionHandler(MissingConfigrationException.class)
  public ResponseEntity<String> missingConfigrationException(
      MissingConfigrationException e
  ) {
    return responseEntity(e);
  }

  @ExceptionHandler(ZipcodeTooManyCitiesException.class)
  public ResponseEntity<String> zipcodeTooManyCitiesException(
      ZipcodeTooManyCitiesException e
  ) {
    return responseEntity(e);
  }

}
