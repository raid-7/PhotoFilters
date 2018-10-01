package raid.study.photofilters.filter;

import raid.study.photofilters.spi.ColorTransformationFilter;

public class InverseFilter extends ColorTransformationFilter {
  @Override
  protected void processPixel(int[] pixel) {
    for (int i = 0; i < 3; i++) {
      pixel[i] = 255 - pixel[i];
    }
  }

  @Override
  public String getName() {
    return "Inverse";
  }
}
