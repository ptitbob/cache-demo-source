package org.shipstone.demo.cache.zipcode.services;

import org.shipstone.demo.cache.zipcode.domain.Code;
import org.shipstone.demo.cache.zipcode.repository.CodeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParameterService {

  private final CodeRepository codeRepository;

  public ParameterService(CodeRepository codeRepository) {
    this.codeRepository = codeRepository;
  }

  public Optional<Code> getCode(String famille, Long code) {
    return codeRepository.findByFamilleAndCode(famille, code);
  }

}
