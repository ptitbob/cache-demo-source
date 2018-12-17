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
public class CityDTO implements Serializable {

  private static final long serialVersionUID = -2802427960216910414L;

  @JsonView(Views.List.class)
  @JsonProperty(value = "insee", index = 2)
  private String insee;

  private Long cdc;

  @JsonView(Views.Details.class)
  @JsonProperty(value = "canton-type", index = 6)
  private String cdcLabel;

  private Long administrativeType;

  @JsonView(Views.Details.class)
  @JsonProperty(value = "statut-administratif", index = 3)
  private String administrativeTypeLabel;

  @JsonView(Views.List.class)
  @JsonProperty(value = "region-code", index = 4)
  private String regionCode;

  @JsonView(Views.Details.class)
  @JsonProperty("region")
  private RegionDTO regionDTO;

  @JsonView(Views.List.class)
  @JsonProperty(value = "departement-code", index = 5)
  private String districtCode;

  @JsonView(Views.Details.class)
  @JsonProperty("departement")
  private DistrictDTO districtDTO;

  @JsonView(Views.Details.class)
  @JsonProperty("arrondissement-code")
  private String codeAr;

  @JsonView({Views.Details.class})
  @JsonProperty(value = "canton-code", index = 7)
  private String codeCanton;

  @JsonView(Views.Details.class)
  @JsonProperty("nom-type")
  private String nameType;

  @JsonView(Views.List.class)
  @JsonProperty("nom-majuscule")
  private String upperName;

  @JsonView(Views.Details.class)
  @JsonProperty(value = "nom", index = 0)
  private String lowerName;

}
