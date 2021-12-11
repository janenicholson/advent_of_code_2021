package nz.co.chaosanddarkness.height;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.io.IOUtils;

@Data
public class HeightMap {

    private Integer[][] heights;

    public HeightMap(InputStream stream) {
        try {
            List<String> lines = IOUtils.readLines(stream, Charset.defaultCharset()).stream().collect(Collectors.toList());
            heights = new Integer[lines.size()][0];
            int i = 0;
            for (String line : lines) {
                heights[i] = new Integer[line.length()];
                int j = 0;
                for (Character c : line.toCharArray()) {
                    heights[i][j++] = c - '0';
                }
                i++;
            }
        } catch (IOException e) {
            heights = new Integer[0][0];
        }
    }

    public Integer[] getLowPoints() {
        List<Integer> lowPoints = new ArrayList<>();
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length; j++) {
                if (isLow(i, j)) {
                    lowPoints.add(heights[i][j]);
                }
            }
        }
        return lowPoints.toArray(new Integer[] {});
    }

    public boolean isLow(int i, int j) {
        if (isLower(heights[i][j],i-1,j)) {
            return false;
        }
        if (isLower(heights[i][j],i+1,j)) {
            return false;
        }
        if (isLower(heights[i][j],i,j-1)) {
            return false;
        }
        if (isLower(heights[i][j],i,j+1)) {
            return false;
        }
        return true;
    }

    private boolean isLower(Integer potentialLowPoint, int i, int j) {
        return i >= 0 && i < heights.length && j >= 0 && j < heights[i].length && potentialLowPoint >= heights[i][j];
    }

    public Integer getRisk() {
        return List.of(getLowPoints()).stream().map(i->i+1).collect(Collectors.summingInt(i->i));
    }

    public List<Integer> getBasinSizes() {
        Boolean[][] surveyed = createMask();
        List<Integer> basinSizes = new ArrayList<>();
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length; j++) {
                if (heights[i][j] != 9) {
                    Integer basinSize = explore(i, j, surveyed);
                    if (basinSize > 0) {
                        basinSizes.add(basinSize);
                    }
                }
            }
        }
        return basinSizes;
    }

    public Integer productOfLargestThreeBasins() {
        List<Integer> basinSizes = getBasinSizes();
        basinSizes.sort(Integer::compareTo);
        return basinSizes.get(basinSizes.size() - 1) * basinSizes.get(basinSizes.size() - 2) * basinSizes.get(basinSizes.size() - 3);
    }

    private Integer explore(int i, int j, Boolean[][] surveyed) {
        Integer basinSize = 0;
        Queue<Point> potentialBasinIndices = new LinkedTransferQueue<>();
        potentialBasinIndices.add(new Point(i,j));
        while(potentialBasinIndices.peek() != null) {
            Point point = potentialBasinIndices.poll();
            if (point.exists() && point.height() != 9 && !surveyed[point.getX()][point.getY()]) {
                potentialBasinIndices.add(point.left());
                potentialBasinIndices.add(point.right());
                potentialBasinIndices.add(point.up());
                potentialBasinIndices.add(point.down());
                basinSize += 1;
                surveyed[point.getX()][point.getY()] = true;
            }
        }
        return basinSize;
    }

    private Boolean[][] createMask() {
        Boolean[][] surveyed = new Boolean[heights.length][0];
        for (int i = 0; i < heights.length; i++) {
            surveyed[i] = new Boolean[heights[i].length];
            Arrays.fill(surveyed[i], false);
        }
        return surveyed;
    }

    @Data
    public class Point {
        private final int x;
        private final int y;

        public Point left() {
            return new Point(x - 1, y);
        }

        public Integer height() {
            return heights[x][y];
        }

        public Point right() {
            return new Point(x + 1, y);
        }

        public Point up() {
            return new Point(x, y - 1);
        }

        public Point down() {
            return new Point(x, y + 1);
        }

        public boolean exists() {
            return x >= 0 && x < heights.length && y >= 0 && y < heights[x].length;
        }
    }
}
