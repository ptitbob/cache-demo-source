package org.shipstone.demo.cache.app.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.shipstone.demo.cache.commons.domain.IdentifiedObject;
import org.shipstone.demo.cache.commons.dto.CityDTO;
import org.shipstone.demo.cache.commons.web.Views;
import org.shipstone.demo.cache.commons.web.dto.FollowedDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class PersonDto extends FollowedDto implements IdentifiedObject<String> {

  @JsonView(Views.List.class)
  private Long id;

  @NotNull
  @NotBlank(message = "Le login ne peux pas être vide")
  @Pattern(regexp = "[a-z]+\\-[a-z]+", message = "Le login doit être de la forme xxxxx-x")
  @JsonView(Views.List.class)
  private String login;

  @JsonView(Views.List.class)
  private String firstname;

  @JsonView(Views.List.class)
  private String lastname;

  @JsonView(Views.Details.class)
  private String cityInsee;

  @JsonView(Views.Details.class)
  private String zipcode;

  @JsonView(Views.Details.class)
  private String pingMessage;

  @JsonView(Views.Details.class)
  private CityDTO city;

  private String code;

  @JsonView(Views.Details.class)
  private String message;

  @Override
  public String getUniqueIdentifier() {
    return getLogin();
  }

}
