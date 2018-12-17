package org.shipstone.demo.cache.zipcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ZipcodeApplication {

	private static final Logger logger = LoggerFactory.getLogger(ZipcodeApplication.class);

	public static void main(String[] args) {
		Environment environment = SpringApplication.run(ZipcodeApplication.class, args).getEnvironment();
    showtime(environment);
	}

  private static void showtime(Environment environment) {
    logger.info("Message : {}", environment.getProperty("message.standard"));
    logger.info("mockEnabled : {}", environment.getProperty("mock.enabled"));
  }

}
