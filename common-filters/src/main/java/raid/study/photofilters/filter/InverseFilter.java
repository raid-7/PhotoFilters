package raid.study.photofilters.filter;

import raid.study.photofilters.spi.ImageOpFilter;

import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;

public class InverseFilter extends ImageOpFilter {
  private static final LookupTable inverseLookupTable;

  static {
    short[] data = new short[256];
    for (int i = 0; i < 256; i++) {
      data[i] = (short) (255 - i);
    }
    inverseLookupTable = new ShortLookupTable(0, data);
  }


  public InverseFilter() {
    super(new LookupOp(inverseLookupTable, null));
  }

  @Override
  public String getName() {
    return "Inverse";
  }
}
