package org.shipstone.demo.cache.pingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Demo de l'utilisation du cache en envirronement Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@SpringBootApplication
public class PingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(PingappApplication.class, args);
	}
}
