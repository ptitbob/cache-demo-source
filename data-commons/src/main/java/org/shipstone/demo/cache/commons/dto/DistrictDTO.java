package org.shipstone.demo.cache.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shipstone.demo.cache.commons.web.Views;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class DistrictDTO implements Serializable {

  private static final long serialVersionUID = 4496870284618815610L;

  @JsonView(Views.Details.class)
  @JsonProperty(value = "region-code", index = 3)
  private String regionCode;

  @JsonView(Views.Identity.class)
  @JsonProperty(value = "code", index = 0)
  private String code;

  @JsonView({Views.Details.class})
  @JsonProperty(value = "chef-lieu", index = 4)
  private String mainCity;

  @JsonView(Views.List.class)
  @JsonProperty(value = "nom-majuscule", index = 1)
  private String upperName;

  @JsonView(Views.Details.class)
  @JsonProperty(value = "nom", index = 2)
  private String lowerName;


}
