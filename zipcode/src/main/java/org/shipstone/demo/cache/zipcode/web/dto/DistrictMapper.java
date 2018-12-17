package org.shipstone.demo.cache.zipcode.web.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.shipstone.demo.cache.commons.dto.DistrictDTO;
import org.shipstone.demo.cache.zipcode.domain.District;

@Mapper(componentModel = "spring")
public interface DistrictMapper {

  DistrictDTO districtDTO(District district);

  @Mappings({
      @Mapping(target = "regionCode", ignore = true),
      @Mapping(target = "lowerName", ignore = true)
  })
  DistrictDTO districtDTOLight(District district);

  District district(DistrictDTO districtDTO);

}
