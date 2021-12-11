package nz.co.chaosanddarkness.segments;

import java.util.Map;
import lombok.Data;

@Data
public class Signal implements Comparable<Signal> {

    private final String segments;

    public static int sortByLength(Signal one, Signal other) {
        int lengthDifference = one.segments.length() - other.segments.length();
        if (lengthDifference == 0) {
            return one.segments.compareTo(other.segments);
        }
        return lengthDifference;
    }

    public int length() {
        return segments.length();
    }

    @Override
    public int compareTo(Signal o) {
        return sortByLength(this, o);
    }

    public String subtract(Signal other) {
        return subtract(other.getSegments());
    }

    public String subtract(String other) {
        StringBuilder result = new StringBuilder();
        for (Character c : segments.toCharArray()) {
            if (!other.contains(String.valueOf(c))) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public String intersect(Signal other) {
        return intersect(other.getSegments());
    }

    public String intersect(String other) {
        StringBuilder result = new StringBuilder();
        for (Character c : segments.toCharArray())
            if (other.contains(String.valueOf(c))) {
                result.append(c);
            }
        return result.toString();
    }

    public String decode(Map<String, String> decoderMapping) {
        StringBuilder decoded = new StringBuilder();
        for (Character c : segments.toCharArray()) {
            decoded.append(decoderMapping.get(String.valueOf(c)));
        }
        return decoded.toString();
    }
}
