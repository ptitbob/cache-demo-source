package org.shipstone.demo.cache.zipcode.repository;

import org.shipstone.demo.cache.zipcode.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>, JpaSpecificationExecutor<Region> {

  Optional<Region> findByCode(String code);

}
