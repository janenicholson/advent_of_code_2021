package nz.co.chaosanddarkness.segments;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;

public class SignalLineTest {

    @Test
    public void solve_for_distinguishables() {
        SignalLine line = construct();
        line.solveSingles();
        assertThat(line.getSolved().get(new Signal("ab")), is("cf"));
        assertThat(line.getSolved().get(new Signal("dab")), is("acf"));
        assertThat(line.getSolved().get(new Signal("eafb")), is("bcdf"));
        assertThat(line.getSolved().get(new Signal("acedgfb")), is("abcdefg"));
    }

    @Test
    public void sort_signal_samples_by_sample_length() {
        SignalLine line = construct();
        assertThat(line.getSamplesByLength().stream().map(Signal::getSegments).collect(Collectors.toList()), contains("ab", "dab", "eafb", "cdfbe", "fbcad", "gcdfa", "cagedb", "cdfgeb", "cefabd", "acedgfb"));
    }

    @Test
    public void can_solve() {
        Map<String, String> mapping = construct().mapping();
        assertThat(mapping, hasEntry("d", "a"));
        assertThat(mapping, hasEntry("e", "b"));
        assertThat(mapping, hasEntry("a", "c"));
        assertThat(mapping, hasEntry("f", "d"));
        assertThat(mapping, hasEntry("g", "e"));
        assertThat(mapping, hasEntry("b", "f"));
        assertThat(mapping, hasEntry("c", "g"));
    }

    @Test
    public void digits_are_5353() {
        assertThat(construct().decodedDigits(), is(5353));
    }

    private SignalLine construct() {
        return new Signals(SignalLineTest.class.getResourceAsStream("signal_small.txt")).getSignals().get(0);
    }
}
