package raid.study.photofilters.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PhotoView extends JComponent {

  private Image image;
  private final PhotoDrawer drawer;

  public PhotoView() {
    drawer = new PhotoDrawer();
    setLayout(new GridBagLayout());
    add(drawer);
    drawer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        controlRatio();
      }
    });
  }

  public void setImage(Image image) {
    this.image = image;
    controlRatio();
    revalidate();
    repaint();
  }

  private void controlRatio() {
    int width = getWidth();
    int height = getHeight();
    if (image == null) {
      drawer.setPreferredSize(new Dimension(width, height));
    } else {
      float ratio = ((float) image.getWidth(null)) / image.getHeight(null);
      int newWidth = Math.round(height * ratio), newHeight;
      if (newWidth > width) {
        newHeight = Math.round(width / ratio);
        newWidth = width;
      } else {
        newHeight = height;
      }
      drawer.setPreferredSize(new Dimension(newWidth, newHeight));
    }
  }

  private class PhotoDrawer extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (image != null) {
        Insets insets = getInsets();
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;
        g.drawImage(image, insets.left, insets.top, width, height, Color.WHITE, null);
      }
    }
  }
}
