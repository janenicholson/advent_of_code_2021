package nz.co.chaosanddarkness.vents;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

public class VentTest {

    @Test
    public void vent_1_1_to_1_3_covers_points_1_1__1_2_and_1_3() {
        assertThat(new Vent("1", "1", "1", "3").points(), contains(
                new Point(1,1), new Point(1,2), new Point(1,3)
                ));
    }

    @Test
    public void vent_9_7_to_7_7_covers_points_9_7__8_7_and_7_7() {
        assertThat(new Vent("9", "7", "7", "7").points(), contains(
                new Point(9,7), new Point(8,7), new Point(7,7)
                ));
    }

    @Test
    public void vent_1_1_to_3_3_covers_points_1_1__2_2_and_3_3() {
        assertThat(new Vent("1", "1", "3", "3").points(), contains(
                new Point(1,1), new Point(2,2), new Point(3,3)
                ));
    }

    @Test
    public void vent_9_7_to_7_9_covers_points_9_7__8_8_and_7_9() {
        assertThat(new Vent("9", "7", "7", "9").points(), contains(
                new Point(9,7), new Point(8,8), new Point(7,9)
                ));
    }

}
