package org.shipstone.demo.cache.zipcode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "region")
  private String code;

  @Column(name = "cheflieu_city_insee")
  private String mainCityInsee;

  @Column(name = "nom_maj")
  private String upperName;

  @Column(name = "nom_min")
  private String lowerName;

}
