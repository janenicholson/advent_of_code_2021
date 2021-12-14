package nz.co.chaosanddarkness.origami;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.io.IOUtils;

@Data
@AllArgsConstructor
public class Origami {

    private Boolean[][] dots;
    private List<FoldInstruction> instructions;

    public Origami(InputStream stream) {
        try {
            List<String> lines = IOUtils.readLines(stream, Charset.defaultCharset());
            List<Point> points = readDots(lines);
            lines = lines.subList(points.size(), lines.size());
            dots = plot(points);
            instructions = parse(lines);
        }
        catch (IOException e) {
        }
    }

    private List<Point> readDots(List<String> lines) {
        ArrayList<Point> points = new ArrayList<Point>();
        for (String line : lines) {
            if (line.trim().isBlank()) {
                return points;
            }
            String[] point = line.split(",");
            points.add(new Point(toInt(point[0]), toInt(point[1])));
        }
        return points;
    }

    private Integer toInt(String point) {
        return Integer.parseInt(point);
    }

    private Boolean[][] plot(List<Point> points) {
        int maxX = 0;
        int maxY = 0;
        for (Point point : points) {
            maxX = Math.max(maxX, point.getX());
            maxY = Math.max(maxY, point.getY());
        }
        Boolean[][] dots = new Boolean[maxY+1][maxX+1];
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                dots[y][x] = false;
            }
        }
        for (Point point : points) {
            dots[point.getY()][point.getX()] = true;
        }
        return dots;
    }

    private List<FoldInstruction> parse(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(s->!s.isBlank())
                .map(FoldInstruction::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public String[] printableDots() {
        String[] printout = new String[dots.length];
        for (int y = 0; y < dots.length; y++) {
            StringBuilder builder = new StringBuilder();
            for (int x = 0; x < dots[y].length; x++) {
                builder.append(dots[y][x] ? "#" : ".");
            }
            printout[y] = builder.toString();
        }
        return printout;
    }

    public void print() {
        String[] printable = printableDots();
        for (String line : printable) {
            System.out.println(line);
        }
    }

    public Origami foldAll() {
        Origami origami = this;
        while (origami.getInstructions().size() > 0) {
            origami = origami.fold();
        }
        return origami;
    }

    public Origami fold() {
        return new Origami(foldDots(), instructions.subList(1, instructions.size()));
    }

    private Boolean[][] foldDots() {
        FoldInstruction instruction = instructions.get(0);
        int newY = newY(instruction);
        Boolean[][] newDots = new Boolean[newY][0];
        for (int y = 0; y < newY; y++) {
            int newX = newX(instruction, dots[y]);
            newDots[y] = new Boolean[newX];
            for (int x = 0; x < newX; x++) {
                switch (instruction.getAction()) {
                case LEFT:
                    newDots[y][x] = dots[y][x] || dots[y][dots[y].length - (x + 1)];
                    break;
                case UP:
                    newDots[y][x] = dots[y][x] || dots[dots.length - (y + 1)][x];
                    break;
                default:
                    newDots[y][x] = dots[y][x];
                    break;
                }
            }
        }
        return newDots;
    }

    private int newY(FoldInstruction instruction) {
        switch (instruction.getAction()) {
        case LEFT:
            return dots.length;
        case UP:
            return Math.max(instruction.getOrigin(), dots.length - (instruction.getOrigin() + 1));
        default:
            return dots.length;
        }
    }

    private int newX(FoldInstruction instruction, Boolean[] dots) {
        switch (instruction.getAction()) {
        case LEFT:
            return Math.max(instruction.getOrigin(), dots.length - (instruction.getOrigin() + 1));
        case UP:
            return dots.length;
        default:
            return dots.length;
        }
    }

    public Integer countDots() {
        Integer dotCount = 0;
        for (int y = 0; y < dots.length; y++) {
            for (int x = 0; x < dots[y].length; x++) {
                if (dots[y][x]) {
                    dotCount++;
                }
            }
        }
        return dotCount;
    }
}
