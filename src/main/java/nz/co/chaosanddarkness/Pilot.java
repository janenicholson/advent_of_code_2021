package nz.co.chaosanddarkness;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.io.IOUtils;

public class Pilot {

    private List<Instruction> instructions;

    public Pilot(InputStream stream) {
        try {
            instructions = IOUtils.readLines(stream, Charset.defaultCharset()).stream().map(Instruction::of).collect(Collectors.toList());
        }
        catch (IOException e) {
            instructions = List.of();
        }
    }

    public Long finalVector() {
        Location location = Location.HOME();

        for (Instruction instruction : instructions) {
            location = location.adjust(instruction);
        }
        return location.getHorizontalPosition() * location.getDepth();
    }

    public Long aimedVector() {
        AimiableLocation location = AimiableLocation.HOME();

        for (Instruction instruction : instructions) {
            location = location.adjust(instruction);
        }
        return location.getHorizontalPosition() * location.getDepth();
    }

    @Data
    public static class Location {
        private final Long horizontalPosition;
        private final Long depth;

        public static Location HOME() {return new Location(0L, 0L);}

        public Location adjust(Instruction instruction) {
            switch (instruction.direction) {
            case down:
                return new Location(horizontalPosition, depth + instruction.distance);
            case forward:
                return new Location(horizontalPosition + instruction.distance, depth);
            case up:
                return new Location(horizontalPosition, depth - instruction.distance);
            default:
                return null;
            }
        }
    }

    @Data
    public static class AimiableLocation {
        private final Long horizontalPosition;
        private final Long depth;
        private final Long aim;

        public static AimiableLocation HOME() {return new AimiableLocation(0L, 0L, 0L);}

        public AimiableLocation adjust(Instruction instruction) {
            switch (instruction.direction) {
            case down:
                return new AimiableLocation(horizontalPosition, depth, aim + instruction.distance);
            case forward:
                return new AimiableLocation(horizontalPosition + instruction.distance, depth + instruction.distance * aim, aim);
            case up:
                return new AimiableLocation(horizontalPosition, depth, aim - instruction.distance);
            default:
                return null;
            }
        }
    }

    public enum Direction {
        forward,
        down,
        up
    }

    @Data
    public static class Instruction {
        private final Direction direction;
        private final Long distance;

        public static Instruction of(String raw) {
            String[] components = raw.split(" ");
            return new Instruction(Direction.valueOf(components[0]), Long.parseLong(components[1]));
        }
    }
}
