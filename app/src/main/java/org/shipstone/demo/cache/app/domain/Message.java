package org.shipstone.demo.cache.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shipstone.demo.cache.commons.domain.IdentifiedObject;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adm_message")
@SequenceGenerator(name = "messageSequenceGenerator", sequenceName = "adm_message_sequence", allocationSize = 10)
public class Message implements IdentifiedObject<String>, Serializable {

  private static final long serialVersionUID = 5284124935064405558L;

  @Id
  @Column(name = "adm_message_id")
  @GeneratedValue(strategy = SEQUENCE, generator = "messageSequenceGenerator")
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "message")
  private String message;

  @Override
  public String getUniqueIdentifier() {
    return code;
  }
}
