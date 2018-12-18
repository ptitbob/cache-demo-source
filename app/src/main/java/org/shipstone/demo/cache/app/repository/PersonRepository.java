package org.shipstone.demo.cache.app.repository;

import org.shipstone.demo.cache.app.domain.Person;
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
@CacheConfig(cacheNames = "people")
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

  @Transactional(readOnly = true)
  @Cacheable(unless = "#result == false")
  Optional<Person> findByLogin(String login);

  @Transactional(readOnly = true)
  @Query("select count(p) > 0 from Person p where upper(p.login) = :login")
  boolean personExist(@Param("login") String login);

  @Transactional
  @CacheEvict(key = "#p0.login")
  void delete(Person person);

  @Transactional
  @CachePut(key = "#result.login", condition = "#result != null")
  Person save(Person person);

}
