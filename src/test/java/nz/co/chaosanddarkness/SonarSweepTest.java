package nz.co.chaosanddarkness;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SonarSweepTest {

    @Test
    public void number_of_increases_is_7() {
        SonarSweep sonarSweep = new SonarSweep(SonarSweepTest.class.getResourceAsStream("sonarsweep.txt"));
        assertThat(sonarSweep.countIncreases(), is(7L));
        assertThat(sonarSweep.countWindowIncreases(), is(5L));
    }

    @Test
    public void number_of_increases_is_unknown() {
        SonarSweep sonarSweep = new SonarSweep(SonarSweepTest.class.getResourceAsStream("sonarsweep_full.txt"));
        assertThat(sonarSweep.countIncreases(), is(1655L));
        assertThat(sonarSweep.countWindowIncreases(), is(1683L));
    }
}
