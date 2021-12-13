package nz.co.chaosanddarkness.octopus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.io.IOUtils;

@Data
@EqualsAndHashCode(of="energyLevel")
public class OctopusCavern {

    private Integer[][] energyLevel;
    private Boolean[][] flashed;
    private int flashes = 0;
    private int totalSteps = 0;

    public OctopusCavern(InputStream stream) {
        try {
            List<String> lines = IOUtils.readLines(stream, Charset.defaultCharset()).stream().collect(Collectors.toList());
            energyLevel = new Integer[lines.size()][0];
            flashed = new Boolean[lines.size()][0];
            int i = 0;
            for (String line : lines) {
                energyLevel[i] = new Integer[line.length()];
                flashed[i] = new Boolean[line.length()];
                int j = 0;
                for (Character c : line.toCharArray()) {
                    flashed[i][j] = false;
                    energyLevel[i][j++] = c - '0';
                }
                i++;
            }
        } catch (IOException e) {
            energyLevel = new Integer[0][0];
        }
    }

    private void resetFlash() {
        for (int i = 0; i < energyLevel.length; i++) {
            for (int j = 0; j < energyLevel[i].length; j++) {
                flashed[i][j] = false;
                if (energyLevel[i][j] > 9) {
                    energyLevel[i][j] = 0;
                }
            }
        }
    }

    public void stepUntilAllFlash() {
        while(!allFlashed()) {
            step(1);
        }
    }

    private boolean allFlashed() {
        for (int i = 0; i < energyLevel.length; i++) {
            for (int j = 0; j < energyLevel[i].length; j++) {
                if (energyLevel[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public OctopusCavern step(int steps) {
        for(int i = 0; i < steps; i++) {
            step();
            resetFlash();
            totalSteps++;
        }
        return this;
    }

    private void step() {
        increaseEnergyLevels();

        while(processFlashes() > 0) {}
    }

    private void increaseEnergyLevels() {
        for (int i = 0; i < energyLevel.length; i++) {
            for (int j = 0; j < energyLevel[i].length; j++) {
                energyLevel[i][j]++;
            }
        }
    }

    private int processFlashes() {
        int flashCount = 0;
        for (int i = 0; i < energyLevel.length; i++) {
            for (int j = 0; j < energyLevel[i].length; j++) {
                Point point = new Point(i, j);
                if (point.exists() && point.level() > 9 && !point.flashed()) {
                    point.flash();
                    flashCount++;
                }
            }
        }
        return flashCount;
    }

    @Data
    public class Point {
        private final int x;
        private final int y;

        public boolean flashed() {
            return flashed[x][y];
        }

        public void flash() {
            flashes++;
            flashed[x][y] = true;
            for (Point adjacent : surroundingPoints()) {
                if (adjacent.exists()) {
                    adjacent.increase();
                }
            }
        }

        public Integer level() {
            return energyLevel[x][y];
        }

        public void increase() {
            energyLevel[x][y]++;
        }

        public Point[] surroundingPoints() {
            return new Point[] {
                    new Point(x-1, y-1), new Point(x-1, y), new Point(x-1, y+1),
                    new Point(x, y-1), new Point(x, y+1),
                    new Point(x+1, y-1), new Point(x+1, y), new Point(x+1, y+1)
                    };
        }

        public boolean exists() {
            return x >= 0 && x < energyLevel.length && y >= 0 && y < energyLevel[x].length;
        }
    }
}
