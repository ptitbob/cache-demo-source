package org.shipstone.demo.cache.app.service;

import org.shipstone.demo.cache.app.web.dto.CacheInformationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Service
public class CacheService {

  private final Logger logger = LoggerFactory.getLogger(CacheService.class);

  private final CacheManager cacheManager;

  public CacheService(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  public List<CacheInformationDto> getList() {
    return cacheManager.getCacheNames().stream().map(CacheInformationDto::new).collect(Collectors.toList());
  }

  public void clearAllCache() {
    logger.info("Suppression du contenu de tous les caches");
    this.cacheManager.getCacheNames().forEach(
        this::clearCache
    );
  }

  public void clearCache(String cacheName) {
    logger.info("Suppression du contenu du cache {}", cacheName);
    // L'appel au cache native directement est lié à un bug de l'implementation de la JSR 107 (javax.cache->cache-api) qui n'a pas correctement implementé le RàZ d'un cache :(
    // https://github.com/ehcache/ehcache3/issues/2364
    getCache(cacheName, cache -> ((JCacheCache)cache).getNativeCache().clear());
  }

  public void evict(String cacheName, final String key) {
    logger.info("Suppression de la valeur du cache {} avec la clé {}", cacheName, key);
    getCache(cacheName, cache -> cache.evict(key));
  }

  private void getCache(String cacheName, Consumer<Cache> consumer) {
    Cache cache = this.cacheManager.getCache(cacheName);
    if (cache != null) {
      consumer.accept(cache);
    }
  }
}
