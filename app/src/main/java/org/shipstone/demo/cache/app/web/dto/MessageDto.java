package org.shipstone.demo.cache.app.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shipstone.demo.cache.commons.domain.IdentifiedObject;
import org.shipstone.demo.cache.commons.web.Views;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements IdentifiedObject<String> {

  private Long id;

  @NotNull
  @Pattern(regexp = "[A-Z]{3,}", message = "Le code doit être en majuscule et comporter au moins 3 lettres")
  @JsonView(Views.List.class)
  private String code;

  @JsonView(Views.Details.class)
  private String message;

  @Override
  public String getUniqueIdentifier() {
    return code;
  }

}
