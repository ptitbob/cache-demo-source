package org.shipstone.demo.cache.zipcode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class District {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "region", length = 2)
  private String regionCode;

  @Column(name = "departement", length = 2)
  private String code;

  @Column(name = "cheflieu_city_insee", length = 6)
  private String mainCityInsee;

  @Column(name = "type_nom")
  private Long nameModifier;

  @Column(name = "nom_maj", length = 70)
  private String upperName;

  @Column(name = "nom_min", length = 70)
  private String lowerName;

}
