package nz.co.chaosanddarkness.segments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Segments {

    public static final String[] ORIGINAL_MAPPING = new String[] {"abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg"};

    private static Map<Integer, List<String>> getValidCombinations() {
        Map<Integer, List<String>> map = new HashMap<>();
        for (String mapItem : ORIGINAL_MAPPING) {
            List<String> list = map.getOrDefault(mapItem.length(), new ArrayList<>());
            list.add(mapItem);
            map.put(mapItem.length(), list);
        }
        return map;
    }

    private static Integer[] getSegmentsPerDigit() {
        Integer[] ints = new Integer[10];
        for (int i = 0; i < 10; i++) {
            ints[i] = ORIGINAL_MAPPING[i].length();
        }
        return ints;
    }

    private static String getSingleMissingSegments() {
        StringBuilder missingSegments = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (ORIGINAL_MAPPING[i].length() == 6) {
                String missingSegment = new Signal("abcdefg").subtract(ORIGINAL_MAPPING[i]);
                if (!missingSegments.toString().contains(missingSegment)) {
                    missingSegments.append(missingSegment);
                }
            }
        }
        return missingSegments.toString();
    }

    public static final Map<Integer, List<String>> VALID_COMBINATIONS = getValidCombinations();
    public static final Integer[] SEGMENTS_PER_DIGIT = getSegmentsPerDigit();
    public static final String SINGLE_MISSING_SEGMENTS = getSingleMissingSegments();
}
