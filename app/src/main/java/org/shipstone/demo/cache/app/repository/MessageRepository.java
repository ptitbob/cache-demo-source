package org.shipstone.demo.cache.app.repository;

import org.shipstone.demo.cache.app.domain.Message;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Repository
@Transactional(readOnly = true)
@CacheConfig(cacheNames = {"message"})
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

  @Cacheable(key = "#p0", unless = "#result == null")
  Optional<Message> findByCode(String code);

  @Query("select count(m) > 0 from Message m where upper(m.code) = :code")
  boolean messageExist(@Param("code") String code);

  boolean existsMessageByCode(String code);

  @Transactional
  @CacheEvict(key = "#p0.code")
  void delete(Message message);

  @Transactional
  @CachePut(key = "#p0.code", unless = "#result == null")
  Message save(Message message);

}
