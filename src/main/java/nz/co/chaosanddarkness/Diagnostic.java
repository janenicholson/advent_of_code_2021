package nz.co.chaosanddarkness;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;

public class Diagnostic {

    private List<String> diagnostics;

    public Diagnostic(InputStream stream) {
        try {
            diagnostics = IOUtils.readLines(stream, Charset.defaultCharset()).stream().collect(Collectors.toList());
        }
        catch (IOException e) {
            diagnostics = List.of();
        }
    }

    public Long calculatePowerConsumption() {
        return gamma() * epsilon();
    }

    public Long gamma() {
        return Long.parseLong(gammaBits(diagnostics), 2);
    }

    private String gammaBits(List<String> diagnostics) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < diagnostics.get(0).length(); i++) {
            result.append(mostCommon(diagnostics, i));
        }
        return result.toString();
    }

    private Character mostCommon(List<String> diagnostics, int i) {
        Map<Character, Long> tally = new HashMap<>(2);
        tally.put('0', 0L);
        tally.put('1', 0L);
        for (String diagnostic : diagnostics) {
            char characterAt = diagnostic.charAt(i);
            tally.put(characterAt, tally.get(characterAt) + 1);
        }
        if (tally.get('0') > tally.get('1')) return '0'; return '1';
    }

    public Long epsilon() {
        return Long.parseLong(epsilonBits(diagnostics), 2);
    }

    private String epsilonBits(List<String> diagnostics) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < diagnostics.get(0).length(); i++) {
            result.append(leastCommon(diagnostics, i));
        }
        return result.toString();
    }

    private Character leastCommon(List<String> diagnostics, int i) {
        Map<Character, Long> tally = new HashMap<>(2);
        tally.put('0', 0L);
        tally.put('1', 0L);
        for (String diagnostic : diagnostics) {
            char characterAt = diagnostic.charAt(i);
            tally.put(characterAt, tally.get(characterAt) + 1);
        }
        if (tally.get('0') <= tally.get('1')) return '0'; return '1';
    }

    public Long calculateLifeSupport() {
        return oxygen() * co2();
    }

    public Long oxygen() {
        String bestMatch = bestMatch(this::gammaBits);
        return Long.parseLong(bestMatch, 2);
    }

    private String bestMatch(Function<List<String>, String> f) {
        List<String> candidates = new ArrayList<>(diagnostics);
        for (int i = 0; i < diagnostics.get(0).length(); i++) {
            String gammaBits = f.apply(candidates);
            candidates = newBestMatch(gammaBits, i, candidates);
            if (candidates.size() == 1) {
                return candidates.get(0);
            }
        }
        return null;
    }

    private List<String> newBestMatch(String gammaBits, int i, List<String> candidates) {
        List<String> nextCandidates = new ArrayList<>();
        for (String candidate : candidates) {
            if (candidate.charAt(i) == gammaBits.charAt(i)) {
                nextCandidates.add(candidate);
            }
        }
        return nextCandidates;
    }

    public Long co2() {
        String bestMatch = bestMatch(this::epsilonBits);
        return Long.parseLong(bestMatch, 2);
    }
}
