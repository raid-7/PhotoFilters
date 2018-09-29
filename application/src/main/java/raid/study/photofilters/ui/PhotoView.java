package raid.study.photofilters.ui;

import javax.swing.*;
import java.awt.*;

public class PhotoView extends JComponent {

  private Image image;

  public PhotoView() {
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }

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

  public void setImage(Image image) {
    this.image = image;
    repaint();
  }
}
