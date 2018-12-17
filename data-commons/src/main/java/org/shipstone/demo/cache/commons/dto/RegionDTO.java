package org.shipstone.demo.cache.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shipstone.demo.cache.commons.web.Views;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class RegionDTO {

  @JsonView(Views.Identity.class)
  @JsonProperty(value = "code", index = 1)
  private String code;

  @JsonView({Views.Details.class})
  @JsonProperty(value = "chef-lieu", index = 3)
  private CityDTO mainCity;

  @JsonView(Views.List.class)
  @JsonProperty(value = "nom-majuscule", index = 0)
  private String upperName;

  @JsonView(Views.Details.class)
  @JsonProperty(value = "nom", index = 2)
  private String lowerName;

  @JsonView(Views.Details.class)
  @JsonProperty(value = "departements", index = 4)
  private List<DistrictDTO> districtList;

}
