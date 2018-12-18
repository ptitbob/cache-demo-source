package org.shipstone.demo.cache.app.web;

import com.fasterxml.jackson.annotation.JsonView;
import org.shipstone.demo.cache.app.service.ZipcodeService;
import org.shipstone.demo.cache.app.service.exception.MissingConfigrationException;
import org.shipstone.demo.cache.commons.dto.CityDTO;
import org.shipstone.demo.cache.commons.web.Views;
import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@RestController
@RequestMapping("cities")
public class ZipcodeController {

  private final Logger logger = LoggerFactory.getLogger(ZipcodeController.class);

  private final ZipcodeService zipcodeService;

  public ZipcodeController(ZipcodeService zipcodeService) {
    this.zipcodeService = zipcodeService;
  }

  @GetMapping("{insee:\\d{5,6}}")
  @JsonView(Views.Details.class)
  public CityDTO getByInsee(
      @PathVariable("insee") String codeInsee
  ) throws MissingConfigrationException, EntityNotFoundException {
    logger.info("HTTP GET/villes?insee={}", codeInsee);
    return zipcodeService.getCityByInsee(codeInsee);
  }

}
