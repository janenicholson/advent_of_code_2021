package nz.co.chaosanddarkness.segments;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class SignalsTest {

    @Test
    public void small_input() {
        List<SignalLine> signals = new Signals(SignalsTest.class.getResourceAsStream("signal_small.txt")).getSignals();
        assertThat(signals, hasSize(1));
        assertThat(signals.get(0).getSamples().stream().map(Signal::getSegments).collect(Collectors.toList()), containsInAnyOrder("acedgfb","cdfbe","gcdfa","fbcad","dab","cefabd","cdfgeb","eafb","cagedb","ab"));
        assertThat(signals.get(0).getDigits().stream().map(Signal::getSegments).collect(Collectors.toList()), containsInAnyOrder("cdfeb","fcadb","cdfeb","cdbaf"));
    }

    @Test
    public void parse_input() {
        assertThat(new Signals(SignalsTest.class.getResourceAsStream("signal.txt")).getSignals(), hasSize(10));
    }

    @Test
    public void distinguishable_digits_occurence_is_26() {
        assertThat(new Signals(SignalsTest.class.getResourceAsStream("signal.txt")).countDistinguishableDigits(), is(26L));
    }

    @Test
    public void can_decode_digits() {
        assertThat(new Signals(SignalsTest.class.getResourceAsStream("signal.txt")).getDigits(), contains(8394, 9781, 1197, 9361, 4873, 8418, 4548, 1625, 8717, 4315));
    }

    @Test
    public void digits_have_sum() {
        assertThat(new Signals(SignalsTest.class.getResourceAsStream("signal.txt")).getDigitSum(), is(61229));
    }

    @Test
    public void distinguishable_digits_occurence_in_full_is_unknown() {
        assertThat(new Signals(SignalsTest.class.getResourceAsStream("signal_full.txt")).countDistinguishableDigits(), is(330L));
    }

    @Test
    public void digits_in_full_have_sum() {
        assertThat(new Signals(SignalsTest.class.getResourceAsStream("signal_full.txt")).getDigitSum(), is(1010472));
    }
}
