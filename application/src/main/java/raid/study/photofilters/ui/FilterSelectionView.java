package raid.study.photofilters.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

public class FilterSelectionView extends JPanel {

  private String selectedId;

  public FilterSelectionView(Map<String, String> idNameMap, Consumer<String> callback) {
    String[] filterIds = idNameMap.keySet().toArray(new String[0]);
    String[] filterNames = Arrays.stream(filterIds).map(idNameMap::get).toArray(String[]::new);

    System.out.println(filterNames.length);
    JList<String> selector = new JList<>(filterNames);
    selector.addListSelectionListener(event -> {
      selectedId = filterIds[event.getFirstIndex()];
      callback.accept(selectedId);
    });

    setLayout(new BorderLayout());
    add(selector, BorderLayout.NORTH);
    selector.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }

  public String getSelectedId() {
    return selectedId;
  }

}
