package org.shipstone.demo.cache.app.web.dto;

import org.mapstruct.Mapper;
import org.shipstone.demo.cache.app.domain.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {

  MessageDto messageDto(Message message);

  Message message(MessageDto messageDto);

}
