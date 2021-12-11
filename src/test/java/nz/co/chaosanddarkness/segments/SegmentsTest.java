package nz.co.chaosanddarkness.segments;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SegmentsTest {

    private static final Map<Integer, List<String>> expectedValidCombinations = Map.of(
            2, List.of("cf"),
            3, List.of("acf"),
            4, List.of("bcdf"),
            5, List.of("acdeg", "acdfg", "abdfg"),
            6, List.of("abcefg", "abdefg", "abcdfg"),
            7, List.of("abcdefg")
            );
    private static final Integer[] expetedSegmentsPerDigit = new Integer[] {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};

    @Test
    public void valid_combinations_can_be_calculated() {
        assertThat(Segments.VALID_COMBINATIONS.size(), is(expectedValidCombinations.size()));
        for (int i = 0; i < 10; i++) {
            if (expectedValidCombinations.get(i) == null) {
                assertThat(Segments.VALID_COMBINATIONS.get(i), is(nullValue()));
            }
            else {
                assertThat(Segments.VALID_COMBINATIONS.get(i), contains(expectedValidCombinations.get(i).toArray()));
            }
        }
    }

    @Test
    public void segments_per_digit_can_be_calculated() {
        assertThat(Segments.SEGMENTS_PER_DIGIT, arrayContaining(expetedSegmentsPerDigit));
    }

    @Test
    public void missing_segments_are_d_c_and_e() {
        assertThat(Segments.SINGLE_MISSING_SEGMENTS, is("dce"));
    }
}
