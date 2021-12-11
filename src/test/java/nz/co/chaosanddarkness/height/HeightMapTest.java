package nz.co.chaosanddarkness.height;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class HeightMapTest {

    @Test
    public void can_parse_input() {
        HeightMap heightMap = construct("smoke_basin.txt");
        assertThat(heightMap.getHeights().length, is(5));
        assertThat(heightMap.getHeights()[0].length, is(10));
        assertThat(heightMap.getHeights()[4], arrayContaining(9,8,9,9,9,6,5,6,7,8));
    }

    @Test
    public void row_1_column_2_is_low() {
        HeightMap heightMap = construct("smoke_basin.txt");
        assertThat(heightMap.isLow(0, 1), is(true));
    }

    @Test
    public void low_points_are_1_0_5_5() {
        HeightMap heightMap = construct("smoke_basin.txt");
        assertThat(heightMap.getLowPoints(), arrayContaining(1,0,5,5));
    }

    @Test
    public void risk_level_is_15() {
        HeightMap heightMap = construct("smoke_basin.txt");
        assertThat(heightMap.getRisk(), is(15));
    }

    @Test
    public void risk_level_is_unknown() {
        HeightMap heightMap = construct("smoke_basin_full.txt");
        assertThat(heightMap.getRisk(), is(633));
    }

    @Test
    public void find_all_basins() {
        HeightMap heightMap = construct("smoke_basin.txt");
        assertThat(heightMap.getBasinSizes(), containsInAnyOrder(3, 9, 14, 9));
    }

    @Test
    public void basin_size_is_1134() {
        HeightMap heightMap = construct("smoke_basin.txt");
        assertThat(heightMap.productOfLargestThreeBasins(), is(1134));
    }

    @Test
    public void basin_size_is_unknown() {
        HeightMap heightMap = construct("smoke_basin_full.txt");
        assertThat(heightMap.productOfLargestThreeBasins(), is(1050192));
    }

    private HeightMap construct(String resource) {
        return new HeightMap(HeightMapTest.class.getResourceAsStream(resource));
    }

}
