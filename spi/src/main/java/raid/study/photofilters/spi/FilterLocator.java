package raid.study.photofilters.spi;

import java.util.*;

public class FilterLocator {
  private static FilterLocator instance;

  private Map<String, String> idNameMap = new TreeMap<>();
  private Map<String, Filter> filters = new HashMap<>();

  private FilterLocator(ServiceLoader<Filter> loader) {
    for (Filter filter : loader) {
      idNameMap.put(filter.getId(), filter.getName());
      filters.put(filter.getId(), filter);
    }
  }

  public Map<String, String> getIdNameMap() {
    return Collections.unmodifiableMap(idNameMap);
  }

  public Filter getFilter(String id) {
    return filters.get(id);
  }

  public static FilterLocator getInstance() {
    if (instance == null) {
      instance = new FilterLocator(ServiceLoader.load(Filter.class));
    }
    return instance;
  }
}
