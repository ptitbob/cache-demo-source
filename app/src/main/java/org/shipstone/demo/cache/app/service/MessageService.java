package org.shipstone.demo.cache.app.service;

import org.shipstone.demo.cache.app.domain.Message;
import org.shipstone.demo.cache.app.repository.MessageRepository;
import org.shipstone.demo.cache.commons.web.exceptions.EntityAlreadyExistException;
import org.shipstone.demo.cache.commons.web.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@Service
public class MessageService {

  private final Logger logger = LoggerFactory.getLogger(MessageService.class);

  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public Page<Message> getAll(Pageable pageable) {
    return messageRepository.findAll(pageable);
  }

  public Message getMessageByCode(String code) throws EntityNotFoundException {
    long start = System.currentTimeMillis();
    try {
      logger.info("\t --> Find message [{}] (SQL)", code);
      Message message = messageRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Message", code, "404-ME01"));
      logger.info("\t\t --> {} ms", System.currentTimeMillis() - start);
      return message;
    } catch (EntityNotFoundException e) {
      logger.info("\t --> NOT FOUND message [{}]", code);
      throw e;
    }
  }

  public Message createMessage(Message message) throws EntityAlreadyExistException {
    logger.info("\t--> create message [{}]", message);
    if (messageRepository.messageExist(message.getCode())) {
      throw new EntityAlreadyExistException("400-ME02", message);
    }
    return messageRepository.save(message);
  }

  public Message updateMessage(Message message) throws EntityNotFoundException {
    logger.info("\t--> update message[{}]", message);
    Message messageToUpdate = getMessageByCode(message.getCode());
    messageToUpdate.setMessage(message.getMessage());
    return messageRepository.save(messageToUpdate);
  }

  public void deleteMessageByCode(String code) throws EntityNotFoundException {
    logger.info("\t--> delete message [{}]", code);
    Message message = getMessageByCode(code);
    messageRepository.delete(message);
  }
}
