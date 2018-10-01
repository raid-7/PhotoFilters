package raid.study.photofilters;

import raid.study.photofilters.spi.Filter;
import raid.study.photofilters.spi.FilterLocator;
import raid.study.photofilters.ui.UIConstants;
import raid.study.photofilters.ui.UIEventType;
import raid.study.photofilters.ui.Window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class Main {
  private Window viewContainer;
  private final BlockingQueue<Runnable> hardTaskQueue = new ArrayBlockingQueue<>(16);
  private volatile BufferedImage openedImage;
  private volatile BufferedImage filteredImage;

  private Main() {
    Map<String, String> filtersIdNameMap = FilterLocator.getInstance().getIdNameMap();

    scheduleUITask(() -> {
      viewContainer = new Window(filtersIdNameMap);

      viewContainer
          .addListener(UIEventType.FILTER_SELECTED, (Consumer<String>) id -> scheduleHardTask(() -> refilter(id)));

      viewContainer.addListener(UIEventType.WINDOW_CLOSING, this::exit);

      viewContainer.addListener(UIEventType.OPEN_ACTION, () -> {
        File file = viewContainer.getDialogs().openFileDialog();
        if (file != null) {
          scheduleHardTask(() -> loadImage(file));
        }
      });

      viewContainer.addListener(UIEventType.SAVE_ACTION, () -> {
        if (filteredImage == null) {
          viewContainer.getDialogs().errorDialog(UIConstants.NOTHING_TO_SAVE_MESSAGE);
        } else {
          File file = viewContainer.getDialogs().saveFileDialog();
          if (file != null) {
            scheduleHardTask(() -> saveImage(file));
          }
        }
      });
    });
  }


  private void exit() {
    scheduleHardTask(() -> System.exit(0));
  }

  private void loadImage(File from) {
    try {
      openedImage = ImageIO.read(from);
    } catch (IOException exc) {
      openedImage = null;
      handleException(exc);
    }

    scheduleUITask(() -> {
      viewContainer.setBeforeImage(openedImage);
      String selectedId = viewContainer.getSelectedId();
      if (selectedId != null)
        scheduleHardTask(() -> refilter(selectedId));
    });
  }

  private void saveImage(File to) {
    String format = recognizeFormat(to.getName());
    try {
      ImageIO.write(filteredImage, format, to);
    } catch (IOException exc) {
      handleException(exc);
    }
  }

  private String recognizeFormat(String name) {
    String extension = null;
    int pointPosition = name.lastIndexOf('.');
    if (pointPosition >= 0)
      extension = name.substring(pointPosition + 1).toLowerCase();
    if (extension == null || extension.length() != 3)
      extension = "png";
    return extension;
  }

  private void refilter(String filterId) {
    System.out.println(filterId);

    if (openedImage == null)
      filteredImage = null;
    else {
      Filter filter = FilterLocator.getInstance().getFilter(filterId);
      filteredImage = filter.transform(openedImage);
    }

    scheduleUITask(() -> viewContainer.setAfterImage(filteredImage));
  }

  private void scheduleUITask(Runnable runnable) {
    SwingUtilities.invokeLater(runnable);
  }

  private void scheduleHardTask(Runnable runnable) {
    hardTaskQueue.add(runnable);
  }

  private void handleException(Exception exc) {
    handleException(exc, null);
  }

  private void handleException(Exception exc, String problem) {
    exc.printStackTrace();
    scheduleUITask(() -> viewContainer.getDialogs().errorDialog(problem == null ? exc.getMessage() : problem));
  }

  private void run() {
    scheduleUITask(() -> viewContainer.show());

    try {
      while (!Thread.interrupted()) {
        Runnable task = hardTaskQueue.take();
        task.run();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Main main = new Main();
    main.run();
  }
}
