package nz.co.chaosanddarkness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class ExponentialGrowthTest {

    @Test
    public void read_input() {
        Long[] lanternfishAges = construct().getLanternfishByAge();
        assertThat(lanternfishAges[1], is(1L));
        assertThat(lanternfishAges[2], is(1L));
        assertThat(lanternfishAges[3], is(2L));
        assertThat(lanternfishAges[4], is(1L));
    }

    @Test
    public void state_after_day_1() {
        Long[] lanternfishAges = construct().age(1).getLanternfishByAge();
        assertThat(lanternfishAges[0], is(1L));
        assertThat(lanternfishAges[1], is(1L));
        assertThat(lanternfishAges[2], is(2L));
        assertThat(lanternfishAges[3], is(1L));
    }

    @Test
    public void state_after_day_2() {
        Long[] lanternfishAges = construct().age(2).getLanternfishByAge();
        assertThat(lanternfishAges[0], is(1L));
        assertThat(lanternfishAges[1], is(2L));
        assertThat(lanternfishAges[2], is(1L));
        assertThat(lanternfishAges[6], is(1L));
        assertThat(lanternfishAges[8], is(1L));
    }

    @Test
    public void state_after_day_3() {
        Long[] lanternfishAges = construct().age(3).getLanternfishByAge();
        assertThat(lanternfishAges[0], is(2L));
        assertThat(lanternfishAges[1], is(1L));
        assertThat(lanternfishAges[5], is(1L));
        assertThat(lanternfishAges[6], is(1L));
        assertThat(lanternfishAges[7], is(1L));
        assertThat(lanternfishAges[8], is(1L));
    }

    @Test
    public void state_after_day_4() {
        Long[] lanternfishAges = construct().age(4).getLanternfishByAge();
        assertThat(lanternfishAges[0], is(1L));
        assertThat(lanternfishAges[4], is(1L));
        assertThat(lanternfishAges[5], is(1L));
        assertThat(lanternfishAges[6], is(3L));
        assertThat(lanternfishAges[7], is(1L));
        assertThat(lanternfishAges[8], is(2L));
    }

    @Test
    public void state_after_day_9() {
        Long[] lanternfishAges = construct().age(9).getLanternfishByAge();
        assertThat(lanternfishAges[0], is(1L));
        assertThat(lanternfishAges[1], is(3L));
        assertThat(lanternfishAges[2], is(2L));
        assertThat(lanternfishAges[3], is(2L));
        assertThat(lanternfishAges[4], is(1L));
        assertThat(lanternfishAges[5], is(0L));
        assertThat(lanternfishAges[6], is(1L));
        assertThat(lanternfishAges[7], is(0L));
        assertThat(lanternfishAges[8], is(1L));
    }

    @Test
    public void state_after_day_18() {
        Long[] lanternfishAges = construct().age(18).getLanternfishByAge();
        assertThat(lanternfishAges[0], is(3L));
        assertThat(lanternfishAges[1], is(5L));
        assertThat(lanternfishAges[2], is(3L));
        assertThat(lanternfishAges[3], is(2L));
        assertThat(lanternfishAges[4], is(2L));
        assertThat(lanternfishAges[5], is(1L));
        assertThat(lanternfishAges[6], is(5L));
        assertThat(lanternfishAges[7], is(1L));
        assertThat(lanternfishAges[8], is(4L));
    }

    @Test
    public void count_after_day_18() {
        Long lanternfishCount = construct().age(18).numberOfLanternFish();
        assertThat(lanternfishCount, is(26L));
    }

    @Test
    public void count_after_day_80() {
        Long lanternfishCount = construct().age(80).numberOfLanternFish();
        assertThat(lanternfishCount, is(5934L));
    }

    private ExponentialGrowth construct() {
        return new ExponentialGrowth(ExponentialGrowthTest.class.getResourceAsStream("lanternfishages.txt"));
    }

    @Test
    public void count_full_after_day_80() {
        Long lanternfishCount = construct_full().age(80).numberOfLanternFish();
        assertThat(lanternfishCount, is(345793L));
    }

    @Test
    public void count_full_after_day_256() {
        Long lanternfishCount = construct_full().age(256).numberOfLanternFish();
        assertThat(lanternfishCount, is(1572643095893L));
    }

    private ExponentialGrowth construct_full() {
        return new ExponentialGrowth(ExponentialGrowthTest.class.getResourceAsStream("lanternfishages_full.txt"));
    }
}
