package nz.co.chaosanddarkness.vents;

import lombok.Data;

@Data
public class Dimension {
    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;
}
