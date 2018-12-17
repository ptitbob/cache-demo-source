package org.shipstone.demo.cache.zipcode.web.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.shipstone.demo.cache.commons.dto.RegionDTO;
import org.shipstone.demo.cache.zipcode.domain.Region;

@Mapper(componentModel = "spring")
public interface RegionMapper {

  RegionDTO regionDTO(Region region);

  @Mappings({
      @Mapping(target = "mainCity", ignore = true),
      @Mapping(target = "lowerName", ignore = true)
  })
  RegionDTO regionDTOLight(Region region);

  Region region(RegionDTO regionDTO);

}
