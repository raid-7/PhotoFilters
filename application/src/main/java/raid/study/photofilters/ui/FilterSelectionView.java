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

    JList<String> selector = new JList<>(filterNames);
    selector.addListSelectionListener(event -> {
      String newId = filterIds[selector.getSelectedIndex()];
      //noinspection StringEquality
      if (selectedId != newId) {
        selectedId = newId;
        callback.accept(selectedId);
      }
    });
    selector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    setLayout(new BorderLayout());
    add(selector, BorderLayout.NORTH);
    selector.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }

  public String getSelectedId() {
    return selectedId;
  }

}
