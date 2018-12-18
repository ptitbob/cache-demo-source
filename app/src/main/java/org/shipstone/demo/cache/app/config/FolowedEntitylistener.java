package org.shipstone.demo.cache.app.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class FolowedEntitylistener implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of("rogue_one");
  }

}
