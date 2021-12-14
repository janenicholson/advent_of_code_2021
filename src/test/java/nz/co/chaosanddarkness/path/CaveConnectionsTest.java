package nz.co.chaosanddarkness.path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Test;

public class CaveConnectionsTest {

    @Test
    public void parse_input() {
        assertThat(construct("pathways.txt").getConnections().get("start"), hasItem("A"));
    }

    @Test
    public void pathway_count() {
        assertThat(construct("pathways.txt").uniquePaths(), hasSize(10));
    }

    @Test
    public void pathway_count_allowing_2_traversals() {
        assertThat(construct("pathways.txt").uniquePathsAllowingOneExtraVisit(), hasSize(36));
    }

    @Test
    public void example_2_pathway_count() {
        assertThat(construct("pathways_19.txt").uniquePaths(), hasSize(19));
    }

    @Test
    public void example_2_pathway_count_allowing_2_traversals() {
        assertThat(construct("pathways_19.txt").uniquePathsAllowingOneExtraVisit(), hasSize(103));
    }

    @Test
    public void example_3_pathway_count() {
        assertThat(construct("pathways_226.txt").uniquePaths(), hasSize(226));
    }

    @Test
    public void example_3_pathway_count_allowing_2_traversals() {
        assertThat(construct("pathways_226.txt").uniquePathsAllowingOneExtraVisit(), hasSize(3509));
    }

    @Test
    public void full_pathway_count() {
        assertThat(construct("pathways_full.txt").uniquePaths(), hasSize(3410));
    }

    @Test
    public void full_pathway_count_allowing_2_traversals() {
        assertThat(construct("pathways_full.txt").uniquePathsAllowingOneExtraVisit(), hasSize(98796));
    }

    private CaveConnections construct(String resource) {
        return new CaveConnections(CaveConnectionsTest.class.getResourceAsStream(resource));
    }

}
