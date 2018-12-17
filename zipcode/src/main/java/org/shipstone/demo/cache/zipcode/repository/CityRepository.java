package org.shipstone.demo.cache.zipcode.repository;

import org.shipstone.demo.cache.zipcode.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

  Optional<City> findByInsee(String mainCityInsee);

}
