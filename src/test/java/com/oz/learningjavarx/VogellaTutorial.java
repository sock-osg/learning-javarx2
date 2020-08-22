package com.oz.learningjavarx;

import io.reactivex.Maybe;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import org.junit.Test;
import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

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

  /**
   * Observable<T>: Emits 0 or n items and terminates with an success or an error event.
   */
  @Test
  public void testWithObservable() {
    Observable<String> stringObservable = Observable.create(emitter -> {
      try {
        List<String> todos = this.getTodos();
        for (String todo : todos) {
          emitter.onNext(todo);
        }
        emitter.onComplete();
      } catch (Exception e) {
        emitter.onError(e);
      }
    });
  }

  /**
   * Maybe<T>: Succeeds with an item, or no item, or errors. The reactive version of an Optional.
   */
  @Test
  public void testWithMaybe() {
    Maybe<List<String>> todoMaybe = Maybe.create(emitter -> {
      try {
        List<String> todos = this.getTodos();
        if (todos != null && !todos.isEmpty()) {
          emitter.onSuccess(todos);
        } else {
          emitter.onComplete();
        }
      } catch (Exception genExc) {
        emitter.onError(genExc);
      }
    });

    // I can use this way for subscribe
    Disposable disposableSubscriber = todoMaybe.subscribe(System.out::print, Throwable::printStackTrace);

    // or this one...
    todoMaybe.subscribeWith(new DisposableMaybeObserver<List<String>>() {
      @Override
      public void onSuccess(List<String> strings) {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });

    disposableSubscriber.dispose(); // Stops listening
  }

  private List<String> getTodos() {
    return Arrays.asList("Hello", "Fucking", "World");
  }
}
