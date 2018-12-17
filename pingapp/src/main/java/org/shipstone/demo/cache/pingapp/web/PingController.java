package org.shipstone.demo.cache.pingapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */@RestController
public class PingController {

  public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:SS");

  private final Logger logger = LoggerFactory.getLogger(PingController.class);

  @GetMapping
  public String getPingForPerson(
      @RequestParam(value = "login", defaultValue = "john doe") String login
  ) {
    int waitLoop = new Random().nextInt(4);
    logger.info("PING - login {} - waiting loop : {}", login, waitLoop);
    try {
      Thread.sleep((1000 * waitLoop) + 1000);
    } catch (InterruptedException e) {
      // nothing
    }
    logger.debug("PING (SLEEP) - login {} - waiting loop : {}", login, waitLoop);
    return String.format(
        "Ping pour %s @ timestamp : %s",
        login,
        LocalDateTime.now().format(FORMATTER)
    );
  }

}
