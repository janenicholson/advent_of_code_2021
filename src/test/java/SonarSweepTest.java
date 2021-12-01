import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;
import org.junit.Test;
import org.mockito.internal.util.io.IOUtil;

public class SonarSweepTest {

    @Test
    public void number_of_increases_is_7() {
        List<String> text = (List<String>)IOUtil.readLines(SonarSweepTest.class.getResourceAsStream("sonarsweep.txt"));
        SonarSweep sonarSweep = new SonarSweep();
        assertThat(sonarSweep.countIncreases(text), is(7L));
        assertThat(sonarSweep.countWindowIncreases(text), is(5L));
    }

    @Test
    public void number_of_increases_is_unknown() {
        List<String> text = (List<String>)IOUtil.readLines(SonarSweepTest.class.getResourceAsStream("sonarsweep_full.txt"));
        SonarSweep sonarSweep = new SonarSweep();
        assertThat(sonarSweep.countIncreases(text), is(1655L));
        assertThat(sonarSweep.countWindowIncreases(text), is(1683L));
    }
}
