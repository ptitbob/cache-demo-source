package org.shipstone.demo.cache.zipcode.web;

import com.fasterxml.jackson.annotation.JsonView;
import org.shipstone.demo.cache.commons.dto.RegionDTO;
import org.shipstone.demo.cache.commons.web.ResponseEntityProcessor;
import org.shipstone.demo.cache.commons.web.Views;
import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;
import org.shipstone.demo.cache.zipcode.domain.Region;
import org.shipstone.demo.cache.zipcode.services.CityService;
import org.shipstone.demo.cache.zipcode.services.DistrictService;
import org.shipstone.demo.cache.zipcode.services.RegionService;
import org.shipstone.demo.cache.zipcode.tools.TimerMockProcessor;
import org.shipstone.demo.cache.zipcode.web.dto.CityMapper;
import org.shipstone.demo.cache.zipcode.web.dto.DistrictMapper;
import org.shipstone.demo.cache.zipcode.web.dto.RegionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("regions")
public class RegionController implements ResponseEntityProcessor, TimerMockProcessor {

  private final Logger logger = LoggerFactory.getLogger(RegionController.class);

  private final RegionService regionService;

  private final RegionMapper regionMapper;

  private final CityService cityService;

  private final CityMapper cityMapper;

  private final DistrictService districtService;

  private final DistrictMapper districtMapper;

  @Value("${mock.enabled}")
  private Boolean mockEnabled;

  public RegionController(RegionService regionService, RegionMapper regionMapper, CityService cityService, CityMapper cityMapper, DistrictService districtService, DistrictMapper districtMapper) {
    this.regionService = regionService;
    this.regionMapper = regionMapper;
    this.cityService = cityService;
    this.cityMapper = cityMapper;
    this.districtService = districtService;
    this.districtMapper = districtMapper;
  }

  @GetMapping
  @JsonView(Views.List.class)
  public ResponseEntity<List<RegionDTO>> getAll(
      @RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "nom", required = false) String name,
      @PageableDefault() Pageable pageable
  ) {
    logger.info("HIT getAll({} , {} , {}", code, name, pageable);
    processMock();
    return responseEntity(regionService.getAll(code, name, pageable), regionMapper::regionDTO);
  }

  @GetMapping("{code:\\d{2}}")
  @JsonView(Views.Details.class)
  public RegionDTO getRegionByCode(
      @PathVariable("code") String code,
      @RequestParam(value = "inclure-departements", defaultValue = "false") boolean includeDistrictList
  ) throws EntityNotFoundException {
    logger.info("HIT getRegionByCode({}, {})", code, includeDistrictList);
    processMock();
    Region region = regionService.getRegionByCode(code);
    RegionDTO regionDTO = regionMapper.regionDTO(region);
    regionDTO.setMainCity(cityMapper.cityDTOLightForRegion(cityService.getCityByInsee(region.getMainCityInsee())));
    if (includeDistrictList) {
      regionDTO.setDistrictList(
          districtService.getAll(regionDTO.getCode(), PageRequest.of(0, Integer.MAX_VALUE))
              .getContent().stream()
              .map(districtMapper::districtDTOLight)
              .collect(Collectors.toList())
      );
    }
    return regionDTO;
  }

  @Override
  public Boolean getMockEnabled() {
    return mockEnabled;
  }
}
