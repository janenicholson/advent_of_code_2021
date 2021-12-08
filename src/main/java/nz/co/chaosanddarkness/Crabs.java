package nz.co.chaosanddarkness;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import lombok.Data;

@Data
public class Crabs {

    private List<Integer> crabPositions;

    public Crabs(InputStream stream) {
        try {
            List<String> lines = IOUtils.readLines(stream, Charset.defaultCharset()).stream().collect(Collectors.toList());
            for (String line : lines) {
                if (!line.isBlank()) {
                    crabPositions = List.of(line.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
                }
            }
        }
        catch (IOException e) {
        }
    }

    public Integer calculatedBestFitPosition() {
        return (int)Math.round(Math.sqrt(crabPositions.stream().map(x->x*x).collect(Collectors.summingInt(x->x))) / crabPositions.size());
    }

    public Integer linearBestFuelSpend() {
        Integer previousBest = Integer.MAX_VALUE;
        Integer min = crabPositions.stream().collect(Collectors.minBy(Integer::compareTo)).get();
        Integer max = crabPositions.stream().collect(Collectors.maxBy(Integer::compareTo)).get();
        for (int i = min; i <= max; i++) {
            Integer candidate = linearFuelToPosition(i);
            if (candidate < previousBest) {
                previousBest = candidate;
            }
        }
        return previousBest;
    }

    public Integer linearFuelToPosition(int position) {
        return crabPositions.stream().map(p->Math.abs(p - position)).collect(Collectors.summingInt(x->x));
    }

    public Integer geometricFuelToPosition(int position) {
        return crabPositions.stream().map(p->Math.abs(p - position)).map(Crabs::geometricFuelForDistance).collect(Collectors.summingInt(x->x));
    }

    public static Integer geometricFuelForDistance(int distance) {
        if (distance == 0) {
            return 0;
        }
        return distance + geometricFuelForDistance(distance - 1);
    }

    public Integer geometricBestFuelSpend() {
        Integer previousBest = Integer.MAX_VALUE;
        Integer min = crabPositions.stream().collect(Collectors.minBy(Integer::compareTo)).get();
        Integer max = crabPositions.stream().collect(Collectors.maxBy(Integer::compareTo)).get();
        for (int i = min; i <= max; i++) {
            Integer candidate = geometricFuelToPosition(i);
            if (candidate < previousBest) {
                previousBest = candidate;
            }
        }
        return previousBest;
    }
}
