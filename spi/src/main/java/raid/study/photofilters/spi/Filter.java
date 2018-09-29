package raid.study.photofilters.spi;

import java.awt.image.BufferedImage;

public interface Filter {
  BufferedImage transform(BufferedImage sourceImage);
  String getName();

  default String getId() {
    return getClass().getCanonicalName();
  }
}
