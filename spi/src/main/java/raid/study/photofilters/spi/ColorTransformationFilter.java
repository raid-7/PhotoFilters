package raid.study.photofilters.spi;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public abstract class ColorTransformationFilter implements Filter {

  @Override
  public BufferedImage transform(BufferedImage sourceImage) {
    WritableRaster raster = sourceImage.copyData(null);
    process(raster);
    ColorModel colorModel = sourceImage.getColorModel();
    boolean isAlphaPremultiplied = sourceImage.isAlphaPremultiplied();
    return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
  }

  private void process(WritableRaster raster) {
    int[] pixel = null;
    for (int i = 0; i < raster.getWidth(); i++)
      for (int j = 0; j < raster.getHeight(); j++) {
        pixel = raster.getPixel(i, j, pixel);
        processPixel(pixel);
        raster.setPixel(i, j, pixel);
      }
  }

  protected abstract void processPixel(int[] pixel);

}
