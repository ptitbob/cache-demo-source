package org.shipstone.demo.cache.app.web.dto;

import org.mapstruct.Mapper;
import org.shipstone.demo.cache.app.domain.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {

  PersonDto personDto(Person person);

  Person person(PersonDto personDto);

}
