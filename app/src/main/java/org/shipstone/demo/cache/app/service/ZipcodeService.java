package org.shipstone.demo.cache.app.service;

import org.shipstone.demo.cache.app.repository.ZipcodeRepository;
import org.shipstone.demo.cache.app.service.exception.MissingConfigrationException;
import org.shipstone.demo.cache.app.service.exception.ZipcodeTooManyCitiesException;
import org.shipstone.demo.cache.commons.dto.CityDTO;
import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Service
public class ZipcodeService {

  private final Logger logger = LoggerFactory.getLogger(ZipcodeService.class);

  private final ZipcodeRepository zipcodeRepository;

  public ZipcodeService(ZipcodeRepository zipcodeRepository) {
    this.zipcodeRepository = zipcodeRepository;
  }

  public CityDTO getCityByInsee(String cityInseeCode) throws MissingConfigrationException, EntityNotFoundException {
    logger.info("\t --> getCityByInsee({})", cityInseeCode);
    return zipcodeRepository.getCityByInsee(cityInseeCode);
  }

  public Page<CityDTO> getCityListForZipcode(String zipcode, Pageable pageable) throws MissingConfigrationException {
    Page<CityDTO> page = zipcodeRepository.getCityPageByZipcode(zipcode, pageable);
    List<CityDTO> cityDTOList = page.getContent().stream().map(cityDTO -> {
      CityDTO cityDTOReturned = new CityDTO();
      cityDTOReturned.setInsee(cityDTO.getInsee());
      cityDTOReturned.setUpperName(cityDTO.getUpperName());
      return cityDTOReturned;
    }).collect(Collectors.toList());
    return new PageImpl<>(cityDTOList, page.getPageable(), page.getTotalElements());
  }

  public String getCityInseeByZipcode(String zipcode) throws EntityNotFoundException, ZipcodeTooManyCitiesException, MissingConfigrationException {
    List<CityDTO> cityDTOList = zipcodeRepository.getCityListByZipcode(zipcode);
    if (cityDTOList.isEmpty()) {
      throw new EntityNotFoundException("Ville", zipcode, "404-ZI01");
    }
    if (cityDTOList.size() > 1) {
      throw new ZipcodeTooManyCitiesException(zipcode);
    }
    return cityDTOList.get(0).getInsee();
  }
}
