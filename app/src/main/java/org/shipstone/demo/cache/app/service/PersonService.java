package org.shipstone.demo.cache.app.service;

import org.apache.commons.lang3.StringUtils;
import org.shipstone.demo.cache.app.domain.Person;
import org.shipstone.demo.cache.app.repository.PersonRepository;
import org.shipstone.demo.cache.app.repository.PingEchoRepository;
import org.shipstone.demo.cache.app.repository.ZipcodeRepository;
import org.shipstone.demo.cache.app.service.exception.HttpServerErrorException;
import org.shipstone.demo.cache.app.service.exception.MissingConfigrationException;
import org.shipstone.demo.cache.app.service.exception.NoReachableHttpServerException;
import org.shipstone.demo.cache.app.service.exception.PersonNotFoundException;
import org.shipstone.demo.cache.app.web.dto.PersonDto;
import org.shipstone.demo.cache.app.web.dto.PersonMapper;
import org.shipstone.demo.cache.commons.data.DataSpecification;
import org.shipstone.demo.cache.commons.web.exceptions.EntityAlreadyExistException;
import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Service
public class PersonService {

  private final Logger logger = LoggerFactory.getLogger(PersonService.class);

  private final PersonRepository personRepository;

  private final PersonMapper personMapper;

  private final PingEchoRepository pingEchoRepository;

  private final ZipcodeRepository zipcodeRepository;

  private final MessageService messageService;

  public PersonService(PersonRepository personRepository, PersonMapper personMapper, PingEchoRepository pingEchoRepository, ZipcodeRepository zipcodeRepository, MessageService messageService) {
    this.personRepository = personRepository;
    this.personMapper = personMapper;
    this.pingEchoRepository = pingEchoRepository;
    this.zipcodeRepository = zipcodeRepository;
    this.messageService = messageService;
  }

  public Page<Person> getAll(String login, String firstname, String lastname, Pageable pageable) {
    return personRepository.findAll(
        new DataSpecification<Person>("*")
            .condition("login", null, login)
            .condition("firstname", null, firstname)
            .condition("lastname", null, lastname)
        ,
        pageable
    );
  }

  public Person getPersonByLogin(String login) throws PersonNotFoundException {
    return personRepository.findByLogin(login).orElseThrow(() -> new PersonNotFoundException(login));
  }

  public PersonDto getPersonDtoByLogin(String login) throws EntityNotFoundException, NoReachableHttpServerException, HttpServerErrorException, MissingConfigrationException, MissingConfigrationException, HttpServerErrorException {
    logger.warn("--====== START ======--");
    long start = System.currentTimeMillis();
    logger.info("\tAsk Person (SQL)");
    PersonDto person = findPersonDtoByLogin(login);
    long startPingMessage = System.currentTimeMillis();
    logger.info("\tAsk PingMessage (HTTP)", System.currentTimeMillis() - startPingMessage);
    person.setPingMessage(pingEchoRepository.getPingEchoFor(person.getLogin()));
    if (isNotBlank(person.getCityInsee())) {
      long startCity = System.currentTimeMillis();
      logger.info("\tAsk CityByInsee (HTTP)", System.currentTimeMillis() - startCity);
      person.setCity(zipcodeRepository.getCityByInsee(person.getCityInsee()));
    }
    logger.info("\tAsk CodeMessage (SQL)");
    updateMessageForCode(person);
    logger.warn("--====== END ({} ms) ======--", System.currentTimeMillis() - start);
    return person;
  }

  private PersonDto findPersonDtoByLogin(String login) throws PersonNotFoundException {
    logger.info("\t --> Find person({}) (SQL)", login);
    long startSQL = System.currentTimeMillis();
    PersonDto person = personMapper.personDto(personRepository.findByLogin(login).orElseThrow(() -> new PersonNotFoundException(login)));
    logger.info("\t\t --> {} ms", System.currentTimeMillis() - startSQL);
    return person;
  }

  private void updateMessageForCode(PersonDto person) {
    if (StringUtils.isNotBlank(person.getCode())) {
      try {
        person.setMessage(messageService.getMessageByCode(person.getCode()).getMessage());
      } catch (EntityNotFoundException e) {
        logger.warn("Code {} non localisé", person.getCode());
        if (logger.isTraceEnabled()) {
          logger.trace("Exception", e);
        }
      }
    }
  }

  public void deleteByLogin(String login) throws PersonNotFoundException {
    personRepository.delete(getPersonByLogin(login));
  }

  public Person createPerson(Person person) throws EntityAlreadyExistException {
    if (personRepository.personExist(person.getLogin())) {
      throw new EntityAlreadyExistException("400-PE02", person);
    }
    return personRepository.save(person);
  }

  public Person updatePerson(Person person) throws PersonNotFoundException {
    Person personToUpdate = getPersonAndUpdateCityInformations(person);
    updateName(person, personToUpdate);
    personToUpdate.setCode(person.getCode());
    return personRepository.save(personToUpdate);
  }

  public void updateName(Person person, Person personToUpdate) {
    personToUpdate.setFirstname(person.getFirstname());
    personToUpdate.setLastname(person.getLastname());
  }

  public Person getPersonAndUpdateCityInformations(Person person) throws PersonNotFoundException {
    Person personToUpdate = getPersonByLogin(person.getLogin());
    personToUpdate.setCityInsee(person.getCityInsee());
    personToUpdate.setZipcode(person.getZipcode());
    return personToUpdate;
  }

  public Person updatePersonForCity(Person person) throws PersonNotFoundException {
    Person personToUpdate = getPersonAndUpdateCityInformations(person);
    return personRepository.save(personToUpdate);
  }

  public Person updatePersonForName(Person person) throws PersonNotFoundException {
    Person personToUpdate = getPersonByLogin(person.getLogin());
    updateName(person, personToUpdate);
    return personRepository.save(personToUpdate);
  }
}
