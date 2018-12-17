package org.shipstone.demo.cache.zipcode.services.exceptions;

import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;

public class CityNotFoundException extends EntityNotFoundException {

  public CityNotFoundException(String insee) {
    super("City", insee,  "CIT-404");
  }

}
