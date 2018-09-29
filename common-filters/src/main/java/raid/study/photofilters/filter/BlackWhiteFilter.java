package raid.study.photofilters.filter;

import raid.study.photofilters.spi.ImageOpFilter;

import java.awt.color.ColorSpace;
import java.awt.image.ColorConvertOp;

public class BlackWhiteFilter extends ImageOpFilter {

  public BlackWhiteFilter() {
    super(new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null));
  }

  @Override
  public String getName() {
    return "Black&White";
  }
}
