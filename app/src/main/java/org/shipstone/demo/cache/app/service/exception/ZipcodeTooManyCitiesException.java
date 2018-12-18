package org.shipstone.demo.cache.app.service.exception;

import org.shipstone.demo.cache.commons.web.exceptions.RegisteredException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ZipcodeTooManyCitiesException extends RegisteredException {

  public ZipcodeTooManyCitiesException(String zipcode) {
    super(BAD_REQUEST, "400-ZI01", String.format("Le code postal %s reference plusieurs villes", zipcode));
  }

}
