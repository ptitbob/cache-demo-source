package org.shipstone.demo.cache.app.repository;

import org.apache.commons.lang3.StringUtils;
import org.shipstone.demo.cache.app.service.exception.MissingConfigrationException;
import org.shipstone.demo.cache.commons.dto.CityDTO;
import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static org.springframework.http.HttpMethod.GET;

@Repository
@CacheConfig(cacheNames = "city")
public class ZipcodeRepository {

  public static final String X_TOTAL_ELEMENT = "x-total-element";

  public static final String X_CURRENT_PAGE = "x-current-page";

  public static final String X_PAGE_SIZE = "x-page-size";

  private final Logger logger = LoggerFactory.getLogger(ZipcodeRepository.class);

  private final String zipcodeServerUrl;

  public ZipcodeRepository(
      @Value("${app.server.zipcode:null}") String zipcodeServerUrl
  ) {
    this.zipcodeServerUrl = zipcodeServerUrl;
  }

  @Cacheable
  public CityDTO getCityByInsee(String cityInseeCode) throws MissingConfigrationException, EntityNotFoundException {
    checkConfiguration();
    logger.info("\t\t-> getCityByInsee({}) HIT !", cityInseeCode);
    long start = System.currentTimeMillis();
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity httpEntity = prepareHttpEntity();
    UriComponentsBuilder uriComponentsBuilder = getCityUriComponentsBuilder().path(cityInseeCode);
    try {
      ResponseEntity<CityDTO> responseEntity = restTemplate.exchange(
          uriComponentsBuilder.build().toUri(),
          GET,
          httpEntity,
          CityDTO.class
      );
      if (responseEntity.hasBody()) {
        return responseEntity.getBody();
      }
      logger.warn("Impossible de recupérer la ville n° INSEE {}", cityInseeCode);
    } catch (RestClientException e) {
      logger.warn("Erreur lors de la récupération de ville - n° INSEE {}", cityInseeCode);
      if (logger.isTraceEnabled()) {
        logger.trace("Exception", e);
      }
      if (e instanceof HttpClientErrorException && HttpStatus.NOT_FOUND.equals(((HttpClientErrorException) e).getStatusCode())) {
        throw new EntityNotFoundException("Ville", cityInseeCode, "404-CI01");
      }
    } finally {
      logger.info("\t\t\t {} ms", System.currentTimeMillis() - start);
    }
    return null;
  }

  private void checkConfiguration() throws MissingConfigrationException {
    if (StringUtils.isBlank(zipcodeServerUrl)) {
      throw new MissingConfigrationException("500-ZI01", "app.server.zipcode");
    }
  }

  private UriComponentsBuilder getCityUriComponentsBuilder() {
    return UriComponentsBuilder
        .fromHttpUrl(zipcodeServerUrl + "villes/");
  }

  private HttpEntity prepareHttpEntity() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return new HttpEntity(httpHeaders);
  }

  public List<CityDTO> getCityListByZipcode(String zipcode) throws MissingConfigrationException {
    return getCityPageByZipcode(zipcode, PageRequest.of(0, MAX_VALUE)).getContent();
  }

  public Page<CityDTO> getCityPageByZipcode(String zipcode, Pageable pageable) throws MissingConfigrationException {
    checkConfiguration();
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity httpEntity = prepareHttpEntity();
    UriComponentsBuilder uriComponentsBuilder = getCityUriComponentsBuilder().
        queryParam("code-postal", zipcode).
        queryParam("size", pageable.getPageSize()).
        queryParam("page", pageable.getPageNumber());
    try {
      ResponseEntity<List<CityDTO>> responseEntity = restTemplate.exchange(
          uriComponentsBuilder.build().toUri(),
          GET,
          httpEntity,
          new ParameterizedTypeReference<List<CityDTO>>() {
          }
      );
      if (responseEntity.hasBody()) {
        int pageSize = 0;
        int pageNumber = 0;
        int totalElement = 0;
        if (responseEntity.getHeaders() != null) {
          if (responseEntity.getHeaders().containsKey(X_PAGE_SIZE) && !responseEntity.getHeaders().get(X_PAGE_SIZE).isEmpty()) {
            pageSize = Integer.valueOf(responseEntity.getHeaders().get(X_PAGE_SIZE).get(0));
          }
          if (responseEntity.getHeaders().containsKey(X_CURRENT_PAGE) && !responseEntity.getHeaders().get(X_CURRENT_PAGE).isEmpty()) {
            pageNumber = Integer.valueOf(responseEntity.getHeaders().get(X_CURRENT_PAGE).get(0));
          }
          if (responseEntity.getHeaders().containsKey(X_TOTAL_ELEMENT) && !responseEntity.getHeaders().get(X_TOTAL_ELEMENT).isEmpty()) {
            totalElement = Integer.valueOf(responseEntity.getHeaders().get(X_TOTAL_ELEMENT).get(0));
          }
        }
        return new PageImpl<>(responseEntity.getBody(), PageRequest.of(pageNumber, pageSize), totalElement);
      }
    } catch (RestClientException e) {
      logger.warn("Erreur lors de la récupération de la liste des villes pour un code postal");
      if (logger.isTraceEnabled()) {
        logger.trace("Exception", e);
      }
    }
    return new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 0), 0);
  }

}
