package org.shipstone.demo.cache.zipcode.tools;

import java.util.Random;

public interface TimerMockProcessor {

  Boolean getMockEnabled();

  default void processMock() {
    if (getMockEnabled()) {
      try {
        Thread.sleep(1000 * new Random().nextInt(5));
      } catch (InterruptedException e) {

      }
    }
  }

}
