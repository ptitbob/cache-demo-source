package org.shipstone.demo.cache.zipcode.repository;

import org.shipstone.demo.cache.zipcode.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long>, JpaSpecificationExecutor<District> {

  Optional<District> findByCode(String code);

}
