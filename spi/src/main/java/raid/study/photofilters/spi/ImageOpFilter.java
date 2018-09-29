package raid.study.photofilters.spi;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public abstract class ImageOpFilter implements Filter {
  private BufferedImageOp operation;

  public ImageOpFilter(BufferedImageOp operation) {
    this.operation = operation;
  }

  public BufferedImage transform(BufferedImage sourceImage) {
    return operation.filter(sourceImage, null);
  }
}
