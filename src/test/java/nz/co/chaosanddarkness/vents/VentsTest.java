package nz.co.chaosanddarkness.vents;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class VentsTest {

    @Test
    public void import_10_lines() {
        Vents vents = construct();
        assertThat(vents.getVents(), hasSize(10));
    }

    @Test
    public void import_lines() {
        Vents vents = construct();
        assertThat(vents.planeSize(), is(new Dimension(0, 0, 9, 9)));
    }

    @Test
    public void four_horizontal_vents() {
        Vents vents = construct();
        assertThat(vents.horizontalVents(), is(4L));
    }

    @Test
    public void two_vertical_vents() {
        Vents vents = construct();
        assertThat(vents.verticalVents(), is(2L));
    }

    @Test
    public void six_vertical_or_horizontal_vents() {
        Vents vents = construct();
        assertThat(vents.horizontalOrVerticalVents(), is(6L));
    }

    @Test
    public void print_horizontal_or_vertical_vents() {
        Vents vents = construct();
        vents.printHorizontalOrVerticalVents();
    }

    @Test
    public void points_containing_a_horizontal_or_vertical_vent() {
        Vents vents = construct();
        assertThat(vents.pointsWithAnyHorizontalOrVerticalVent(), is(21L));
    }

    @Test
    public void points_containing_at_least_2_horizontal_or_vertical_vents() {
        Vents vents = construct();
        assertThat(vents.pointsWithAtLeast2HorizontalOrVerticalVents(), is(5L));
    }

    @Test
    public void print_horizontal_or_vertical_or_diagonal_vents() {
        Vents vents = construct();
        vents.print();
    }

    @Test
    public void points_containing_at_least_2_horizontal_or_vertical_or_diagonal_vents() {
        Vents vents = construct();
        assertThat(vents.pointsWithAtLeast2Vents(), is(12L));
    }

    private Vents construct() {
        return new Vents(VentsTest.class.getResourceAsStream("vents.txt"));
    }

    @Test
    public void points_containing_at_least_2_horizontal_or_vertical_vents_full_input() {
        Vents vents = construct_full();
        assertThat(vents.pointsWithAtLeast2HorizontalOrVerticalVents(), is(7269L));
    }

    @Test
    public void points_containing_at_least_2_horizontal_or_vertical_or_diagonal_vents_full_input() {
        Vents vents = construct_full();
        assertThat(vents.pointsWithAtLeast2Vents(), is(21140L));
    }

    private Vents construct_full() {
        return new Vents(VentsTest.class.getResourceAsStream("vents_full.txt"));
    }
}
