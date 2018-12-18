package org.shipstone.demo.cache.app.web;

import org.shipstone.demo.cache.app.service.PingService;
import org.shipstone.demo.cache.app.service.exception.HttpServerErrorException;
import org.shipstone.demo.cache.app.service.exception.MissingConfigrationException;
import org.shipstone.demo.cache.app.service.exception.NoReachableHttpServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@RestController
@RequestMapping("ping")
public class PingController {

  private final Logger logger = LoggerFactory.getLogger(PingController.class);

  private final PingService pingService;

  public PingController(PingService pingService) {
    this.pingService = pingService;
  }

  @GetMapping
  public String pingMessage(
      @RequestParam(value = "login", required = false) String login
  ) throws HttpServerErrorException, NoReachableHttpServerException, MissingConfigrationException {
    logger.info("HTTP GET/ping");
    return pingService.pingMessage(login);
  }

}
