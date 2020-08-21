package com.oz.learningjavarx;

import org.junit.Test;
import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;

public class VogellaTutorial {

  private String result = "";

  @Test
  public void returnAValue() {
    System.out.printf("[%s] Starting test\n", Thread.currentThread().getId());

    Observable<String> observable = Observable.just("Hello"); // provides data
    // Subscribe a callback that is going to be executed when observable emits an item.
    observable.subscribe(itemEmitted -> {
      result = itemEmitted;
      System.out.printf("[%s] Setting value: %s\n", Thread.currentThread().getId(), itemEmitted);
    });
    observable.subscribe(itemEmitted -> {
      System.out.printf("[%s] Just printing value: %s\n", Thread.currentThread().getId(), itemEmitted);
    });
    assertEquals("Hello", result);
  }
}
