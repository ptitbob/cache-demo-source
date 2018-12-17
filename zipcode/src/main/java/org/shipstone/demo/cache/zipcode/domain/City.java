package org.shipstone.demo.cache.zipcode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class City {

  @Id @Column(name = "id")
  private String id;

  @Column(name = "insee", length = 6)
  private String insee;

  @Column(name = "cdc")
  private Long cdc;

  @Column(name = "cheflieu")
  private Long administrativeType;

  @Column(name = "region", length = 2)
  private String regionCode;

  @Column(name = "departement", length = 3)
  private String districtCode;

  @Column(name = "code", length = 3)
  private String code;

  @Column(name = "code_ar", length = 1)
  private String codeAr;

  @Column(name = "code_canton", length = 2)
  private String codeCanton;

  @Column(name = "type_nom")
  private Long nameType;

  @Column(name = "art_maj", length = 5)
  private String upperArticle;

  @Column(name = "nom_maj", length = 70)
  private String upperName;

  @Column(name = "art_min", length = 5)
  private String lowerArticle;

  @Column(name = "nom_min", length = 70)
  private String lowerName;

}
