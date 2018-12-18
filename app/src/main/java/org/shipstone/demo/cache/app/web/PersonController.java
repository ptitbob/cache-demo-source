package org.shipstone.demo.cache.app.web;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.StringUtils;
import org.shipstone.demo.cache.app.domain.Person;
import org.shipstone.demo.cache.app.service.PersonService;
import org.shipstone.demo.cache.app.service.ZipcodeService;
import org.shipstone.demo.cache.app.service.exception.*;
import org.shipstone.demo.cache.app.web.dto.PersonDto;
import org.shipstone.demo.cache.app.web.dto.PersonMapper;
import org.shipstone.demo.cache.commons.web.ResponseIdentifiedObjectProcessor;
import org.shipstone.demo.cache.commons.web.Views;
import org.shipstone.demo.cache.commons.web.exceptions.EntityAlreadyExistException;
import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;
import org.shipstone.demo.cache.commons.web.validation.ConsistencyEndpointException;
import org.shipstone.demo.cache.commons.web.validation.ConsistencyUpdateProcessor;
import org.shipstone.demo.cache.commons.web.validation.ParamsValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.OK;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@RestController
@RequestMapping("people")
public class PersonController implements ResponseIdentifiedObjectProcessor, ConsistencyUpdateProcessor {

  private final Logger logger = LoggerFactory.getLogger(PersonController.class);

  private final PersonService personService;

  private final PersonMapper personMapper;

  private final ZipcodeService zipcodeService;

  public PersonController(PersonService personService, PersonMapper personMapper, ZipcodeService zipcodeService) {
    this.personService = personService;
    this.personMapper = personMapper;
    this.zipcodeService = zipcodeService;
  }

  @GetMapping
  @JsonView(Views.List.class)
  public ResponseEntity<List<PersonDto>> getAll(
      @RequestParam(value = "login", required = false) String login,
      @RequestParam(value = "prenom", required = false) String firstname,
      @RequestParam(value = "nom", required = false) String lastname,
      @PageableDefault(sort = {"login"}, direction = ASC) Pageable pageable
  ) {
    return responseEntity(personService.getAll(login, firstname, lastname, pageable), personMapper::personDto);
  }

  @GetMapping("{login:[a-z]+\\-[a-z]+}")
  @JsonView(Views.Details.class)
  public PersonDto getPersonByLogin(
      @PathVariable("login") String login
  ) throws EntityNotFoundException, NoReachableHttpServerException, HttpServerErrorException, MissingConfigrationException {
    logger.info("HTTP GET /people/{}", login);
    return personService.getPersonDtoByLogin(login);
  }

  @DeleteMapping("{login:[a-z]+\\-[a-z]+}")
  public ResponseEntity<Void> deleteById(
      @PathVariable("login") String login
  ) {
    try {
      personService.deleteByLogin(login);
    } catch (PersonNotFoundException e) {
      logger.warn("Tentative de suppression d'une personne non existante");
      if (logger.isTraceEnabled()) {
        logger.trace("EXCEPTION", e);
      }
    }
    return new ResponseEntity<>(OK);
  }

  @PostMapping
  public ResponseEntity<PersonDto> createPerson(
      UriComponentsBuilder uriComponentsBuilder,
      @RequestHeader HttpHeaders requestHeaders,
      @Valid @RequestBody PersonDto personDto,
      BindingResult bindingResult
  ) throws ParamsValidationException, EntityAlreadyExistException, ZipcodeTooManyCitiesException, EntityNotFoundException, MissingConfigrationException {
    validateParams(bindingResult);
    updateCityInsseByZipcode(personDto);
    Person createdPerson = personService.createPerson(personMapper.person(personDto));
    return responseEntity(personMapper.personDto(createdPerson), requestHeaders, uriComponentsBuilder);
  }

  private void updateCityInsseByZipcode(@RequestBody @Valid PersonDto personDto) throws EntityNotFoundException, ZipcodeTooManyCitiesException, MissingConfigrationException {
    if (isBlank(personDto.getCityInsee()) && StringUtils.isNotBlank(personDto.getZipcode())) {
      personDto.setCityInsee(zipcodeService.getCityInseeByZipcode(personDto.getZipcode()));
    }
  }

  @PutMapping("{login:[a-z]+\\-[a-z]+}")
  public ResponseEntity<PersonDto> updatePerson(
      @PathVariable("login") String login,
      @RequestHeader HttpHeaders requestHeaders,
      @RequestBody @Valid PersonDto personDto,
      BindingResult bindingResult
  ) throws EntityNotFoundException, ParamsValidationException, ConsistencyEndpointException, ZipcodeTooManyCitiesException, MissingConfigrationException {
    validateConsistecy(login, personDto, bindingResult);
    updateCityInsseByZipcode(personDto);
    Person personUpdate = personService.updatePerson(personMapper.person(personDto));
    return responseEntity(personMapper.personDto(personUpdate), requestHeaders);
  }

  @PatchMapping("{login:[a-z]+\\-[a-z]+}")
  public ResponseEntity<PersonDto> updateName(
      @PathVariable("login") String login,
      @RequestHeader HttpHeaders requestHeaders,
      @RequestBody @Valid PersonDto personDto
  ) throws ConsistencyEndpointException, EntityNotFoundException {
    validateUniqueIdentifier(login, personDto);
    Person personUpdate = personService.updatePersonForName(personMapper.person(personDto));
    return responseEntity(personMapper.personDto(personUpdate), requestHeaders);
  }

  @PatchMapping("{login:[a-z]+\\-[a-z]+}/city")
  public ResponseEntity<PersonDto> updateCity(
      @PathVariable("login") String login,
      @RequestHeader HttpHeaders requestHeaders,
      @RequestBody @Valid PersonDto personDto
  ) throws ConsistencyEndpointException, ZipcodeTooManyCitiesException, EntityNotFoundException, MissingConfigrationException {
    validateUniqueIdentifier(login, personDto);
    updateCityInsseByZipcode(personDto);
    Person personUpdate = personService.updatePersonForCity(personMapper.person(personDto));
    return responseEntity(personMapper.personDto(personUpdate), requestHeaders);
  }

  @Override
  public String getFieldValidationErrorCode() {
    return "400-PE01";
  }

  @Override
  public String getBasePath() {
    return "people";
  }

}
