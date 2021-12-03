package nz.co.chaosanddarkness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class DiagnosticTest {

    @Test
    public void diagnostic_is_198() {
        Diagnostic diagnostic = new Diagnostic(DiagnosticTest.class.getResourceAsStream("diagnostic.txt"));
        assertThat(diagnostic.gamma(), is(22L));
        assertThat(diagnostic.epsilon(), is(9L));
        assertThat(diagnostic.calculatePowerConsumption(), is(198L));
        assertThat(diagnostic.oxygen(), is(23L));
        assertThat(diagnostic.co2(), is(10L));
        assertThat(diagnostic.calculateLifeSupport(), is(230L));
    }

    @Test
    public void diagnostic_is_unknown() {
        Diagnostic diagnostic = new Diagnostic(DiagnosticTest.class.getResourceAsStream("diagnostic_full.txt"));
        assertThat(diagnostic.calculatePowerConsumption(), is(2035764L));
        assertThat(diagnostic.calculateLifeSupport(), is(2817661L));
    }
}
