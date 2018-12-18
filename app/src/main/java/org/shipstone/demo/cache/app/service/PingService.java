package org.shipstone.demo.cache.app.service;

import org.shipstone.demo.cache.app.repository.PingEchoRepository;
import org.shipstone.demo.cache.app.service.exception.HttpServerErrorException;
import org.shipstone.demo.cache.app.service.exception.MissingConfigrationException;
import org.shipstone.demo.cache.app.service.exception.NoReachableHttpServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Service
public class PingService {

  private final Logger logger = LoggerFactory.getLogger(PingService.class);

  private final PingEchoRepository pingEchoRepository;

  public PingService(PingEchoRepository pingEchoRepository) {
    this.pingEchoRepository = pingEchoRepository;
  }

  public String pingMessage(final String login) throws HttpServerErrorException, NoReachableHttpServerException, MissingConfigrationException {
    logger.info("\t --> PingService({})", login);
    return pingEchoRepository.getPingEchoFor(login);
  }

}
