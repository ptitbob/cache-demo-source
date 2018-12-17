package org.shipstone.demo.cache.zipcode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Code {

  @Id @Column(name = "id")
  private Long id;

  @Column(name = "famille")
  private String famille;

  @Column(name = "code")
  private Long code;

  @Column(name = "libelle")
  private String libelle;

}
