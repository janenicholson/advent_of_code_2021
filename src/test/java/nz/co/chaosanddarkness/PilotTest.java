package nz.co.chaosanddarkness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class PilotTest {

    @Test
    public void pilot_to_150() {
        Pilot pilot = new Pilot(PilotTest.class.getResourceAsStream("pilot.txt"));
        assertThat(pilot.finalVector(), is(150L));
        assertThat(pilot.aimedVector(), is(900L));
    }

    @Test
    public void pilot_to_unknown() {
        Pilot pilot = new Pilot(PilotTest.class.getResourceAsStream("pilot_full.txt"));
        assertThat(pilot.finalVector(), is(1507611L));
        assertThat(pilot.aimedVector(), is(1880593125L));
    }
}
