package org.shipstone.demo.cache.app.repository;

import org.apache.commons.lang3.StringUtils;
import org.shipstone.demo.cache.app.service.exception.HttpServerErrorException;
import org.shipstone.demo.cache.app.service.exception.MissingConfigrationException;
import org.shipstone.demo.cache.app.service.exception.NoReachableHttpServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpMethod.GET;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Repository
@CacheConfig(cacheNames = "pingEcho")
public class PingEchoRepository {

  private final Logger logger = LoggerFactory.getLogger(PingEchoRepository.class);

  private final String pingServerUrl;

  public PingEchoRepository(
      @Value("${app.server.ping}") String pingServerUrl
  ) {
    this.pingServerUrl = pingServerUrl;
  }

  @Cacheable(key = "#p0 == null ? \"unamed\" : #p0", unless = "#result == null || #result.contains(\"john doe\")")
  public String getPingEchoFor(String login) throws NoReachableHttpServerException, HttpServerErrorException, MissingConfigrationException {
    if (StringUtils.isBlank(pingServerUrl)) {
      throw new MissingConfigrationException("500-PI01", "app.server.ping");
    }
    logger.info("\t\t--> getPingEchoFor({}) - HIT !", login);
    long start = System.currentTimeMillis();
    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
        .fromHttpUrl(pingServerUrl)
        .queryParam("login", login);
    try {
      ResponseEntity<String> responseEntity = restTemplate.exchange(
          uriComponentsBuilder.build().toUri(), GET, null, String.class
      );
      if (responseEntity.hasBody()) {
        return responseEntity.getBody();
      }
    } catch (HttpStatusCodeException e) {
      if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
        throw new NoReachableHttpServerException("404-PI01", uriComponentsBuilder.build().toUri().toString());
      }
      throw new HttpServerErrorException("500-PI01", uriComponentsBuilder.build().toUri().toString(), e);
    } finally {
      logger.info("\t\t\t {} ms", System.currentTimeMillis() - start);
    }
    return null;
  }

}
