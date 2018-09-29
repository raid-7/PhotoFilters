package raid.study.photofilters.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class Window extends EventSource<UIEventType> {
  private final JFrame frame = new JFrame(UIConstants.WINDOW_TITLE);
  private final PhotoView photoBefore = new PhotoView();
  private final PhotoView photoAfter = new PhotoView();
  private final FilterSelectionView filterSelector;
  private final JButton saveButton = new JButton(UIConstants.SAVE_LABEL);
  private final JButton openButton = new JButton(UIConstants.OPEN_LABEL);
  private final UIDialogs dialogs = new UIDialogs(frame);

  public Window(Map<String, String> filtersIdNameMap) {
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        fire(UIEventType.WINDOW_CLOSING);
      }
    });
    frame.setSize(800, 600);
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);

    filterSelector = new FilterSelectionView(filtersIdNameMap, s -> fire(UIEventType.FILTER_SELECTED, s));

    initUILayout();
    initUIEvents();
  }

  public UIDialogs getDialogs() {
    return dialogs;
  }

  public void setBeforeImage(Image image) {
    photoBefore.setImage(image);
  }

  public void setAfterImage(Image image) {
    photoAfter.setImage(image);
  }

  public String getSelectedId() {
    return filterSelector.getSelectedId();
  }

  public void show() {
    frame.setVisible(true);
  }

  private void initUIEvents() {
    saveButton.addActionListener(ev -> fire(UIEventType.SAVE_ACTION));
    openButton.addActionListener(ev -> fire(UIEventType.OPEN_ACTION));
  }

  private void initUILayout() {
    frame.setLayout(new BorderLayout());
    frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel photoContainer = new JPanel(new GridLayout(1, 2, 10, 10));
    photoContainer.add(photoBefore);
    photoContainer.add(photoAfter);
    frame.add(photoContainer, BorderLayout.CENTER);

    JPanel controlsContainer = new JPanel();
    controlsContainer.setLayout(new BorderLayout());
    JPanel filtersPanel = new JPanel();
    filtersPanel.setLayout(new BorderLayout());
    controlsContainer.add(filtersPanel, BorderLayout.CENTER);
    filtersPanel.add(new JLabel(UIConstants.FILTERS_LABEL), BorderLayout.NORTH);
    filtersPanel.add(filterSelector, BorderLayout.CENTER);

    JPanel buttonsContainer = new JPanel();
    buttonsContainer.add(saveButton);
    buttonsContainer.add(openButton);

    controlsContainer.add(buttonsContainer, BorderLayout.SOUTH);
    controlsContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    frame.add(controlsContainer, BorderLayout.EAST);
  }
}
