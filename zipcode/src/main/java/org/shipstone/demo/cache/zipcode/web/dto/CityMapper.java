package org.shipstone.demo.cache.zipcode.web.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.shipstone.demo.cache.commons.dto.CityDTO;
import org.shipstone.demo.cache.zipcode.domain.City;

@Mapper(componentModel = "spring")
public interface CityMapper {

  CityDTO cityDTO(City city);

  @Mappings({
      @Mapping(target = "cdc", ignore = true),
      @Mapping(target = "regionCode", ignore = true),
      @Mapping(target = "codeAr", ignore = true),
      @Mapping(target = "codeCanton", ignore = true),
      @Mapping(target = "administrativeType", ignore = true),
      @Mapping(target = "lowerName", ignore = true)
  })
  CityDTO cityDTOLightForRegion(City city);

  City city(CityDTO cityDTO);

}
