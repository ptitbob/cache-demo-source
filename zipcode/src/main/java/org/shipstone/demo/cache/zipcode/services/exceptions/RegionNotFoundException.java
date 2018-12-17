package org.shipstone.demo.cache.zipcode.services.exceptions;

import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;

public class RegionNotFoundException extends EntityNotFoundException {
  public RegionNotFoundException(String key) {
    super("Region", key, "REG-404");
  }
}
