package org.shipstone.demo.cache.app.service.exception;

import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;

public class PersonNotFoundException extends EntityNotFoundException {

  public PersonNotFoundException(String key) {
    super("personne", key, "404-PE01");
  }

}
