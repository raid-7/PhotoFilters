package raid.study.photofilters.filter;

import raid.study.photofilters.spi.ColorTransformationFilter;

public class SepiaFilter extends ColorTransformationFilter {

  @Override
  public String getName() {
    return "Sepia";
  }

  @Override
  protected void processPixel(int[] pixel) {
    double r = pixel[0];
    double g = pixel[1];
    double b = pixel[2];
    pixel[0] = makeValid(r * .393 + g *.769 + b * .189);
    pixel[1] = makeValid(r * .349 + g *.686 + b * .168);
    pixel[2] = makeValid(r * .272 + g *.534 + b * .131);
  }

  private int makeValid(double colorComponent) {
    int result = (int) Math.round(colorComponent);
    return Math.max(Math.min(result, 255), 0);
  }
}
