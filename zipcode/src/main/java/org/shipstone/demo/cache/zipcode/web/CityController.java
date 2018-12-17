package org.shipstone.demo.cache.zipcode.web;

import com.fasterxml.jackson.annotation.JsonView;
import org.shipstone.demo.cache.commons.dto.CityDTO;
import org.shipstone.demo.cache.commons.web.ResponseEntityProcessor;
import org.shipstone.demo.cache.commons.web.Views;
import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;
import org.shipstone.demo.cache.zipcode.domain.Code;
import org.shipstone.demo.cache.zipcode.services.CityService;
import org.shipstone.demo.cache.zipcode.services.DistrictService;
import org.shipstone.demo.cache.zipcode.services.ParameterService;
import org.shipstone.demo.cache.zipcode.services.RegionService;
import org.shipstone.demo.cache.zipcode.tools.TimerMockProcessor;
import org.shipstone.demo.cache.zipcode.web.dto.CityMapper;
import org.shipstone.demo.cache.zipcode.web.dto.DistrictMapper;
import org.shipstone.demo.cache.zipcode.web.dto.RegionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("villes")
public class CityController implements ResponseEntityProcessor, TimerMockProcessor {

  private final Logger logger = LoggerFactory.getLogger(CityController.class);

  private final CityService cityService;

  private final CityMapper cityMapper;

  private final RegionService regionService;

  private final RegionMapper regionMapper;

  private final DistrictService districtService;

  private final DistrictMapper districtMapper;

  private final ParameterService parameterService;

  @Value("${mock.enabled}")
  private Boolean mockEnabled;

  public CityController(CityService cityService, CityMapper cityMapper, RegionService regionService, RegionMapper regionMapper, DistrictService districtService, DistrictMapper districtMapper, ParameterService parameterService) {
    this.cityService = cityService;
    this.cityMapper = cityMapper;
    this.regionService = regionService;
    this.regionMapper = regionMapper;
    this.districtService = districtService;
    this.districtMapper = districtMapper;
    this.parameterService = parameterService;
  }

  @GetMapping
  @JsonView(Views.List.class)
  public ResponseEntity<List<CityDTO>> getAll(
      @RequestParam(value = "region", required = false) String regionCode,
      @RequestParam(value = "departement", required = false) String districtCode,
      @RequestParam(value = "nom", required = false) String name,
      @RequestParam(value = "type-administratif", required = false) Long cityType,
      @RequestParam(value = "code-postal", required = false) String zipcode,
      @PageableDefault(sort = {"upperName"}, direction = ASC) Pageable pageable
  ) {
    logger.info("HIT getAll({}, {}, {}, {}, {})", regionCode, districtCode, name, cityType, zipcode);
    processMock();
    return responseEntity(cityService.getAll(regionCode, districtCode, name, cityType, zipcode, pageable), cityMapper::cityDTO);
  }

  @GetMapping("{insee:\\d{5,6}}")
  @JsonView(Views.Details.class)
  public CityDTO getByInsee(
    @PathVariable("insee") String codeInsee
  ) throws EntityNotFoundException {
    logger.info("HIT getByInsee({})", codeInsee);
    processMock();
    CityDTO cityDTO = cityMapper.cityDTO(cityService.getCityByInsee(codeInsee));
    cityDTO.setRegionDTO(regionMapper.regionDTOLight(regionService.getRegionByCode(cityDTO.getRegionCode())));
    cityDTO.setRegionCode(null);
    cityDTO.setDistrictDTO(districtMapper.districtDTOLight(districtService.getByCode(cityDTO.getDistrictCode())));
    cityDTO.setDistrictCode(null);
    if (cityDTO.getCdc() != 0) {
      cityDTO.setCdcLabel(parameterService.getCode("CDC", cityDTO.getCdc()).orElse(new Code()).getLibelle());
    }
    if (cityDTO.getAdministrativeType() != 0) {
      cityDTO.setAdministrativeTypeLabel(parameterService.getCode("CHEFLIEU", cityDTO.getAdministrativeType()).orElse(new Code()).getLibelle());
    }
    return cityDTO;
  }

  @Override
  public Boolean getMockEnabled() {
    return mockEnabled;
  }
}
