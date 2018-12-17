package org.shipstone.demo.cache.zipcode.repository;

import org.shipstone.demo.cache.zipcode.domain.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {

  Optional<Code> findByFamilleAndCode(String famille, Long code);

}
