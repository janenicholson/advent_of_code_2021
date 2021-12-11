package nz.co.chaosanddarkness.segments;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SignalTest {

    @Test
    public void subtract_ab_from_abc() {
        assertThat(new Signal("abc").subtract(new Signal("ab")), is("c"));
        assertThat(new Signal("abc").subtract("ab"), is("c"));
    }

    @Test
    public void intersect_abcd_with_cdef() {
        assertThat(new Signal("abcd").intersect(new Signal("cdef")), is("cd"));
        assertThat(new Signal("abcd").intersect("cdef"), is("cd"));
    }
}
