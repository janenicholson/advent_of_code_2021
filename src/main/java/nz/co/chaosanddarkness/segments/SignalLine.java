package nz.co.chaosanddarkness.segments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class SignalLine {

    private final List<Signal> samples;
    private final List<Signal> digits;
    private final Map<Signal, String> solved = new HashMap<>();
    public static SignalLine parse(String[] split) {
        return new SignalLine(signalList(split[0]), signalList(split[1]));
    }

    private static List<Signal> signalList(String split) {
        return Arrays.asList(split.trim().split(" ")).stream().map(Signal::new).collect(Collectors.toList());
    }

    public void solveSingles() {
        List<Signal> samplesByLength = getSamplesByLength();
        for (Signal sample : samplesByLength) {
            if (Segments.VALID_COMBINATIONS.get(sample.length()).size() == 1) {
                solved.put(sample, Segments.VALID_COMBINATIONS.get(sample.length()).get(0));
            }
        }
    }

    public List<Signal> getSamplesByLength() {
        List<Signal> sortedSamples = new ArrayList<>();
        sortedSamples.addAll(samples);
        sortedSamples.sort(Signal::sortByLength);
        return sortedSamples;
    }

    public Map<String, String> mapping() {
        Map<String, String> mapping = new HashMap<>();
        List<Signal> samplesByLength = getSamplesByLength();
        Signal numberOne = samplesByLength.get(0);
        Signal numberSeven = samplesByLength.get(1);
        Signal numberFour = samplesByLength.get(2);
        Signal numberEight = samplesByLength.get(9);
        mapping.put(numberOne.getSegments().substring(0, 1), "cf");
        mapping.put(numberOne.getSegments().substring(1, 2), "cf");
        mapping.put(numberSeven.subtract(numberOne), "a");
        mapping.put(numberFour.subtract(numberOne).substring(0, 1), "bd");
        mapping.put(numberFour.subtract(numberOne).substring(1, 2), "bd");
        for (int i = 3; i < 9; i++) {
            String remaining = samplesByLength.get(i).subtract(numberFour.getSegments() + numberSeven.getSegments());
            if (remaining.length() == 1) {
                mapping.put(remaining, "g");
            }
            if (remaining.length() > 1 && "g".equals(mapping.get(remaining.substring(0, 1)))) {
                mapping.put(remaining.substring(1, 2), "e");
            }
            if (remaining.length() > 1 && "g".equals(mapping.get(remaining.substring(1, 2)))) {
                mapping.put(remaining.substring(0, 1), "e");
            }
        }
        for (int i = 6; i < 9; i++) {
            String sample = numberEight.subtract(samplesByLength.get(i));
            if (sample.length() == 1) {
                String existing = mapping.get(sample);
                String reduced = new Signal(Segments.SINGLE_MISSING_SEGMENTS).intersect(existing);
                String removed = new Signal(existing).subtract(reduced);
                mapping.put(sample, reduced);
                for (String signal : mapping.keySet()) {
                    if (!signal.equals(sample) && mapping.get(signal).contains(reduced)) {
                        mapping.put(signal, removed);
                    }
                }
            }
        }
        return mapping;
    }

    public int decodedDigits() {
        Map<String, String> decoderMapping = mapping();
        List<String> decodedSignals = digits.stream().map(s->s.decode(decoderMapping)).map(this::sorted).collect(Collectors.toList());
        int result = 0;
        for (String signal : decodedSignals) {
            result *= 10;
            for (int i = 0; i < 10; i++) {
                if (Segments.ORIGINAL_MAPPING[i].equals(signal)) {
                    result += i;
                }
            }
        }
        return result;
    }

    private String sorted(String signal) {
        StringBuilder result = new StringBuilder();
        for (Character character = 'a'; character <= 'g'; character++) {
            if (signal.contains(String.valueOf(character))) {
                result.append(character);
            }
        }
        return result.toString();
    }
}
