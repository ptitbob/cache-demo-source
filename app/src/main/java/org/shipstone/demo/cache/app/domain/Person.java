package org.shipstone.demo.cache.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.shipstone.demo.cache.commons.data.Followed;
import org.shipstone.demo.cache.commons.domain.IdentifiedObject;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Entity
@Table(name = "person")
@SequenceGenerator(name = "personSequenceGenerator", sequenceName = "person_sequence", allocationSize = 10)
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Person extends Followed<String> implements IdentifiedObject<String>, Serializable {

  private static final long serialVersionUID = 8540869348609499621L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = SEQUENCE, generator = "personSequenceGenerator")
  private Long id;

  private String login;

  private String firstname;

  private String lastname;

  private String cityInsee;

  private String zipcode;

  @Column(name = "code_message")
  private String code;

  @Override
  public String getUniqueIdentifier() {
    return login;
  }

}
