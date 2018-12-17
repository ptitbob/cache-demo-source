package org.shipstone.demo.cache.zipcode.services.exceptions;

import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;

public class DistrictNotFoundException extends EntityNotFoundException {

  public DistrictNotFoundException(String districtCode) {
    super("Departement", districtCode, "DEP-404");
  }

}
