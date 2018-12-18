package org.shipstone.demo.cache.app.service.exception;

import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public class PersonNotFoundException extends EntityNotFoundException {

  public PersonNotFoundException(String key) {
    super("personne", key, "404-PE01");
  }

}
