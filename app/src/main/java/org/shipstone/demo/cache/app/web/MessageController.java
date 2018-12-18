package org.shipstone.demo.cache.app.web;


import com.fasterxml.jackson.annotation.JsonView;
import org.shipstone.demo.cache.app.service.MessageService;
import org.shipstone.demo.cache.app.web.dto.MessageDto;
import org.shipstone.demo.cache.app.web.dto.MessageMapper;
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

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@RestController
@RequestMapping("messages")
public class MessageController implements ConsistencyUpdateProcessor, ResponseIdentifiedObjectProcessor {

  private final MessageService messageService;

  private final MessageMapper messageMapper;

  private final Logger logger = LoggerFactory.getLogger(MessageController.class);

  public MessageController(MessageService messageService, MessageMapper messageMapper) {
    this.messageService = messageService;
    this.messageMapper = messageMapper;
  }

  @Override
  public String getBasePath() {
    return "admin/messages";
  }

  @Override
  public String getFieldValidationErrorCode() {
    return "400-ME01";
  }

  @GetMapping
  @JsonView(Views.List.class)
  public ResponseEntity<List<MessageDto>> getAll(
      @PageableDefault Pageable pageable
  ) {
    return responseEntity(messageService.getAll(pageable), messageMapper::messageDto);
  }

  @GetMapping("{code:[A-Z]{3,}}")
  @JsonView(Views.Details.class)
  public MessageDto getMessageByCode(
      @PathVariable("code") String code
  ) throws EntityNotFoundException {
    logger.info("HTTP GET /messages/{}", code);
    return messageMapper.messageDto(messageService.getMessageByCode(code));
  }

  @PostMapping(consumes = {APPLICATION_JSON_VALUE})
  @JsonView(Views.Details.class)
  public ResponseEntity<MessageDto> createMessage(
      @RequestBody @Valid MessageDto messageDto,
      BindingResult bindingResult,
      UriComponentsBuilder uriComponentsBuilder,
      @RequestHeader HttpHeaders httpHeaders
  ) throws EntityAlreadyExistException, ParamsValidationException {
    logger.info("HTTP POST /messages");
    validateParams(bindingResult);
    return responseEntity(messageMapper.messageDto(messageService.createMessage(messageMapper.message(messageDto))), httpHeaders, uriComponentsBuilder);
  }

  @PutMapping("{code:[A-Z]{3,}}")
  @JsonView(Views.Details.class)
  public ResponseEntity<MessageDto> updateMessage(
      @PathVariable("code") String code,
      @RequestHeader HttpHeaders httpHeaders,
      @Valid @RequestBody MessageDto messageDto,
      BindingResult bindingResult
  ) throws ParamsValidationException, ConsistencyEndpointException, EntityNotFoundException {
    logger.info("HTTP PUT /messages/{}", code);
    validateConsistecy(code, messageDto, bindingResult);
    return responseEntity(messageMapper.messageDto(messageService.updateMessage(messageMapper.message(messageDto))), httpHeaders);
  }

  @DeleteMapping("{code:[A-Z]{3,}}")
  public ResponseEntity<MessageDto> deleteMessage(
      @PathVariable("code") String code
  ) {
    logger.info("HTTP DELETE /messages/{}", code);
    try {
      messageService.deleteMessageByCode(code);
    } catch (EntityNotFoundException e) {
      logger.warn("Tentative de suppression d'un message inexistant");
      if (logger.isTraceEnabled()) {
        logger.trace("Exception", e);
      }
    }
    return new ResponseEntity<>(OK);
  }

}
