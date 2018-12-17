package org.shipstone.demo.cache.zipcode.services;

import org.shipstone.demo.cache.commons.data.DataSpecification;
import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;
import org.shipstone.demo.cache.zipcode.domain.City;
import org.shipstone.demo.cache.zipcode.domain.Zipcode;
import org.shipstone.demo.cache.zipcode.repository.CityRepository;
import org.shipstone.demo.cache.zipcode.repository.ZipcodeRepository;
import org.shipstone.demo.cache.zipcode.services.exceptions.CityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.shipstone.demo.cache.commons.data.DataSpecification.ValueOperation.EQUAL;
import static org.shipstone.demo.cache.commons.data.DataSpecification.ValueOperation.IN;

@Service
public class CityService {

  private final Logger logger = LoggerFactory.getLogger(CityService.class);

  private final CityRepository cityRepository;

  private final ParameterService parameterService;

  private final ZipcodeRepository zipcodeRepository;

  public CityService(CityRepository cityRepository, ParameterService parameterService, ZipcodeRepository zipcodeRepository) {
    this.cityRepository = cityRepository;
    this.parameterService = parameterService;
    this.zipcodeRepository = zipcodeRepository;
  }

  public City getCityByInsee(String mainCityInsee) throws EntityNotFoundException {
    return cityRepository.findByInsee(mainCityInsee).orElseThrow(() -> new CityNotFoundException(mainCityInsee));
  }

  public Page<City> getAll(String region, String district, String name, Long cityType, String zipcode, Pageable pageable) {
    List<String> zipcodeCityInseeList = null;
    if (isNotBlank(zipcode)) {
      zipcodeCityInseeList = zipcodeRepository.findByZipcode(zipcode).stream().map(Zipcode::getInseeCityId).collect(Collectors.toList());
    }
    return cityRepository.findAll(
        new DataSpecification<City>("*").
            condition("upperName", null, name == null ? null : name.toUpperCase()).
            condition("regionCode", EQUAL, region).
            condition("districtCode", EQUAL, district).
            condition("administrativeType", EQUAL, cityType).
            condition("insee", IN, zipcodeCityInseeList)
        ,
        pageable
    );
  }
}
