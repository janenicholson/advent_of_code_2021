package nz.co.chaosanddarkness.vents;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import lombok.Getter;

@Getter
public class Vents {

    Predicate<Vent> horizontalOrVertical = v->v.isVertical() || v.isHorizontal();
    Predicate<Vent> anyVent = v->true;

    Pattern pattern = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");
    private List<Vent> vents;
    public Vents(InputStream stream) {
        try {
            vents = IOUtils.readLines(stream, Charset.defaultCharset()).stream()
                    .map(s->pattern.matcher(s))
                    .filter(Matcher::find)
                    .filter(m->m.groupCount() == 4)
                    .map(m->new Vent(m.group(1), m.group(2), m.group(3), m.group(4)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            vents = List.of();
        }
    }

    public Dimension planeSize() {
        return new Dimension(
                vents.stream().map(Vent::points).flatMap(List::stream).map(Point::getX).min(Integer::compareTo).get(),
                vents.stream().map(Vent::points).flatMap(List::stream).map(Point::getY).min(Integer::compareTo).get(),
                vents.stream().map(Vent::points).flatMap(List::stream).map(Point::getX).max(Integer::compareTo).get(),
                vents.stream().map(Vent::points).flatMap(List::stream).map(Point::getY).max(Integer::compareTo).get()
                );
    }

    public long horizontalOrVerticalVents() {
        return verticalVents() + horizontalVents();
    }

    public long verticalVents() {
        return vents.stream().filter(Vent::isVertical).count();
    }

    public long horizontalVents() {
        return vents.stream().filter(Vent::isHorizontal).count();
    }

    public void printHorizontalOrVerticalVents() {
        Dimension planeSize = planeSize();
        Integer[][] ventSpace = createVentSpace(planeSize, horizontalOrVertical);
        for (int j = planeSize.getMinY(); j <= planeSize.getMaxY(); j++) {
            for (int i = planeSize.getMinX(); i <= planeSize.getMaxX(); i++) {
                if (ventSpace[i][j] > 0) {
                    System.out.print(ventSpace[i][j]);
                }
                else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public void print() {
        Dimension planeSize = planeSize();
        Integer[][] ventSpace = createVentSpace(planeSize, anyVent);
        for (int j = planeSize.getMinY(); j <= planeSize.getMaxY(); j++) {
            for (int i = planeSize.getMinX(); i <= planeSize.getMaxX(); i++) {
                if (ventSpace[i][j] > 0) {
                    System.out.print(ventSpace[i][j]);
                }
                else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public long pointsWithAnyHorizontalOrVerticalVent() {
        Dimension planeSize = planeSize();
        Integer[][] ventSpace = createVentSpace(planeSize, horizontalOrVertical );
        int count = 0;
        for (int i = planeSize.getMinX(); i <= planeSize.getMaxX(); i++) {
            for (int j = planeSize.getMinY(); j <= planeSize.getMaxY(); j++) {
                if (ventSpace[i][j] > 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    private Integer[][] createVentSpace(Dimension planeSize, Predicate<Vent> condition) {
        return createVentSpace(planeSize.getMaxX() + 1, planeSize.getMaxY() + 1, condition);
    }

    private Integer[][] createVentSpace(int x, int y, Predicate<Vent> condition) {
        Integer[][] ventSpace = plane(x, y);
        for (Vent vent : vents) {
            if (condition.test(vent)) {
                for (Point point : vent.points()) {
                    ventSpace[point.getX()][point.getY()] += 1;
                }
            }
        }
        return ventSpace;
    }

    private Integer[][] plane(int x, int y) {
        Integer[][] plane = new Integer[x][y];
        for (int i = 0; i < x; i++) {
            Arrays.setAll(plane[i], unused->0);
        }
        return plane;
    }

    public long pointsWithAtLeast2HorizontalOrVerticalVents() {
        Dimension planeSize = planeSize();
        Integer[][] ventSpace = createVentSpace(planeSize, horizontalOrVertical);
        int count = 0;
        for (int i = planeSize.getMinX(); i <= planeSize.getMaxX(); i++) {
            for (int j = planeSize.getMinY(); j <= planeSize.getMaxY(); j++) {
                if (ventSpace[i][j] >= 2) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public long pointsWithAtLeast2Vents() {
        Dimension planeSize = planeSize();
        Integer[][] ventSpace = createVentSpace(planeSize, anyVent);
        int count = 0;
        for (int i = planeSize.getMinX(); i <= planeSize.getMaxX(); i++) {
            for (int j = planeSize.getMinY(); j <= planeSize.getMaxY(); j++) {
                if (ventSpace[i][j] >= 2) {
                    count += 1;
                }
            }
        }
        return count;
    }
}
