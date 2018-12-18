package org.shipstone.demo.cache.app.web.dto;

import org.mapstruct.Mapper;
import org.shipstone.demo.cache.app.domain.Message;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Mapper(componentModel = "spring")
public interface MessageMapper {

  MessageDto messageDto(Message message);

  Message message(MessageDto messageDto);

}
