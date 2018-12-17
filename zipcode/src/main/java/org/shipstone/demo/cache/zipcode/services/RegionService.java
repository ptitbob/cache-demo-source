package org.shipstone.demo.cache.zipcode.services;

import org.shipstone.demo.cache.commons.data.DataSpecification;
import org.shipstone.demo.cache.zipcode.domain.Region;
import org.shipstone.demo.cache.zipcode.repository.RegionRepository;
import org.shipstone.demo.cache.zipcode.services.exceptions.RegionNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.shipstone.demo.cache.commons.data.DataSpecification.ValueOperation.EQUAL_IGNORECASE;

@Service
public class RegionService {

  private final RegionRepository regionRepository;

  public RegionService(RegionRepository regionRepository) {
    this.regionRepository = regionRepository;
  }

  public Page<Region> getAll(String code, String name, Pageable pageable) {
    return regionRepository.findAll(
        new DataSpecification<Region>("*").
            condition("code", EQUAL_IGNORECASE, code).
            condition("upperName", null, name),
        pageable
    );
  }

  public Region getRegionByCode(String code) throws RegionNotFoundException {
    return regionRepository.findByCode(code).orElseThrow(() -> new RegionNotFoundException(code));
  }

}
