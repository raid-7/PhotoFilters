package raid.study.photofilters.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventSource<EventKind> {
  private Map<EventKind, List<Consumer<?>>> listeners = new HashMap<>();

  public void addListener(EventKind type, Consumer<?> listener) {
    listeners.computeIfAbsent(type, key -> new ArrayList<>()).add(listener);
  }

  public void addListener(EventKind type, Runnable listener) {
    listeners.computeIfAbsent(type, key -> new ArrayList<>()).add(val -> listener.run());
  }

  protected <Event>void fire(EventKind type, Event event) {
    listeners.computeIfPresent(type, (key, listeners) -> {
      for (Consumer listener : listeners) {
        //noinspection unchecked
        listener.accept(event);
      }
      return listeners;
    });
  }

  protected void fire(EventKind type) {
    fire(type, null);
  }
}
