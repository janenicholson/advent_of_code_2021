package nz.co.chaosanddarkness.octopus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class OctopusCavernTest {

    @Test
    public void can_parse_file() {
        OctopusCavern cavern = construct("energy_level.txt");
        assertThat(cavern.getEnergyLevel(), arrayWithSize(10));
        assertThat(cavern.getEnergyLevel()[0], arrayContaining(5,4,8,3,1,4,3,2,2,3));
        assertThat(cavern.getEnergyLevel()[9], arrayContaining(5,2,8,3,7,5,1,5,2,6));
    }

    @Test
    public void take_first_step() {
        OctopusCavern cavern = construct("energy_level.txt");
        OctopusCavern cavernStep1 = construct("energy_level_step_1.txt");
        assertThat(cavern.step(1), is(cavernStep1));
    }

    @Test
    public void take_second_step() {
        OctopusCavern cavernStep1 = construct("energy_level_step_1.txt");
        OctopusCavern cavernStep2 = construct("energy_level_step_2.txt");
        assertThat(cavernStep1.step(1), is(cavernStep2));
    }

    @Test
    public void take_third_step() {
        OctopusCavern cavernStep2 = construct("energy_level_step_2.txt");
        OctopusCavern cavernStep3 = construct("energy_level_step_3.txt");
        assertThat(cavernStep2.step(1), is(cavernStep3));
    }

    @Test
    public void take_twenty_steps() {
        OctopusCavern cavern = construct("energy_level.txt");
        OctopusCavern cavernStep20 = construct("energy_level_step_20.txt");
        assertThat(cavern.step(20), is(cavernStep20));
    }

    @Test
    public void take_one_hundred_steps() {
        OctopusCavern cavern = construct("energy_level.txt");
        OctopusCavern cavernStep100 = construct("energy_level_step_100.txt");
        assertThat(cavern.step(100), is(cavernStep100));
        assertThat(cavern.getFlashes(), is(1656));
    }

    @Test
    public void step_until_195() {
        OctopusCavern cavern = construct("energy_level.txt");
        cavern.stepUntilAllFlash();
        assertThat(cavern.getTotalSteps(), is(195));
    }

    @Test
    public void take_one_hundred_steps_into_the_unknown() {
        OctopusCavern cavern = construct("energy_level_full.txt").step(100);
        assertThat(cavern.getFlashes(), is(1615));
    }

    @Test
    public void step_until_full() {
        OctopusCavern cavern = construct("energy_level_full.txt");
        cavern.stepUntilAllFlash();
        assertThat(cavern.getTotalSteps(), is(249));
    }

    private OctopusCavern construct(String resource) {
        return new OctopusCavern(OctopusCavernTest.class.getResourceAsStream(resource));
    }

}
