package org.shipstone.demo.cache.zipcode.repository;

import org.shipstone.demo.cache.zipcode.domain.Zipcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ZipcodeRepository extends JpaRepository<Zipcode, Long>, JpaSpecificationExecutor<Zipcode> {

  List<Zipcode> findByZipcode(String zipcode);

}
