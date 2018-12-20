package org.shipstone.demo.cache.app.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shipstone.demo.cache.commons.web.Views;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CacheInformationDto {

  @JsonView(Views.List.class)
  private String name;

}
