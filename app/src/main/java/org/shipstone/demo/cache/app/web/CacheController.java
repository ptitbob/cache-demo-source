package org.shipstone.demo.cache.app.web;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.StringUtils;
import org.shipstone.demo.cache.app.service.CacheService;
import org.shipstone.demo.cache.app.web.dto.CacheInformationDto;
import org.shipstone.demo.cache.commons.web.ResponseEntityProcessor;
import org.shipstone.demo.cache.commons.web.Views;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 * <p>
 * API de gestion du cache (_r_D)
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@RestController
@RequestMapping("caches")
public class CacheController implements ResponseEntityProcessor {

  private final CacheService cacheService;

  public CacheController(CacheService cacheService) {
    this.cacheService = cacheService;
  }

  @GetMapping
  @JsonView(Views.List.class)
  public ResponseEntity<List<CacheInformationDto>> getList(
      @PageableDefault Pageable pageable
  ) {
    return responseEntity(cacheService.getList(), pageable);
  }

  @DeleteMapping
  public ResponseEntity clearAllCache() {
    cacheService.clearAllCache();
    return new ResponseEntity(OK);
  }

  @DeleteMapping("{cacheName}")
  public ResponseEntity clearCache(
      @PathVariable("cacheName") String cacheName,
      @RequestParam(value = "key", required = false) String key
  ) {
    if (StringUtils.isBlank(key)) {
      this.cacheService.clearCache(cacheName);
    } else {
      this.cacheService.evict(cacheName, key);
    }
    return new ResponseEntity(OK);
  }

}
