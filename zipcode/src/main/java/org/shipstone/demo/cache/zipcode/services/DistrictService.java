package org.shipstone.demo.cache.zipcode.services;

import org.shipstone.demo.cache.commons.data.DataSpecification;
import org.shipstone.demo.cache.zipcode.domain.District;
import org.shipstone.demo.cache.zipcode.repository.DistrictRepository;
import org.shipstone.demo.cache.zipcode.services.exceptions.DistrictNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.shipstone.demo.cache.commons.data.DataSpecification.ValueOperation.EQUAL;

@Service
public class DistrictService {

  private final DistrictRepository districtRepository;

  public DistrictService(DistrictRepository districtRepository) {
    this.districtRepository = districtRepository;
  }

  public Page<District> getAll(String regionCode, Pageable pageable) {
    return districtRepository.findAll(
        new DataSpecification<District>().
            condition("regionCode", EQUAL, regionCode)
        ,
        pageable
    );
  }

  public District getByCode(final String districtCode) throws DistrictNotFoundException {
    return districtRepository.findByCode(districtCode).orElseThrow(() -> new DistrictNotFoundException(districtCode));
  }

}
