package org.shipstone.demo.cache.zipcode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zipcode")
@Data @NoArgsConstructor @AllArgsConstructor
public class Zipcode {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "city_insee")
  private String inseeCityId;

  @Column(name = "code_postal")
  private String zipcode;

}
