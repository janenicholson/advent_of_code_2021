package nz.co.chaosanddarkness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class CrabsTest {

    @Test
    public void read_input() {
        assertThat(construct().getCrabPositions(), contains(16,1,2,0,4,2,7,1,2,14));
    }

    @Test
    public void linear_fuel_to_position_1() {
        assertThat(construct().linearFuelToPosition(1), is(41));
    }

    @Test
    public void linear_fuel_to_position_2() {
        assertThat(construct().linearFuelToPosition(2), is(37));
    }

    @Test
    public void linear_fuel_to_position_3() {
        assertThat(construct().linearFuelToPosition(3), is(39));
    }

    @Test
    public void linear_fuel_to_position_10() {
        assertThat(construct().linearFuelToPosition(10), is(71));
    }

    @Test
    public void estimate_best_position() {
        assertThat(construct().calculatedBestFitPosition(), is(2));
    }

    @Test
    public void linear_fuel_to_best_fit() {
        assertThat(construct().linearBestFuelSpend(), is(37));
    }

    @Test
    public void geometric_fuel_for_distance_3() {
        assertThat(Crabs.geometricFuelForDistance(3), is(6));
    }

    @Test
    public void geometric_fuel_for_distance_4() {
        assertThat(Crabs.geometricFuelForDistance(4), is(10));
    }

    @Test
    public void geometric_fuel_for_distance_5() {
        assertThat(Crabs.geometricFuelForDistance(5), is(15));
    }

    @Test
    public void geometric_fuel_for_distance_11() {
        assertThat(Crabs.geometricFuelForDistance(11), is(66));
    }

    @Test
    public void geometric_fuel_to_position_2() {
        assertThat(construct().geometricFuelToPosition(2), is(206));
    }

    @Test
    public void geometric_fuel_to_position_5() {
        assertThat(construct().geometricFuelToPosition(5), is(168));
    }

    private Crabs construct() {
        return new Crabs(CrabsTest.class.getResourceAsStream("crabpositions.txt"));
    }

    @Test
    public void linear_fuel_to_best_fit_full() {
        assertThat(construct_full().linearBestFuelSpend(), is(336040));
    }

    @Test
    public void geometric_fuel_to_best_fit_full() {
        assertThat(construct_full().geometricBestFuelSpend(), is(94813675));
    }

    private Crabs construct_full() {
        return new Crabs(CrabsTest.class.getResourceAsStream("crabpositions_full.txt"));
    }
}
