package nz.co.chaosanddarkness.path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

public class PathTest {

    @Test
    public void can_traverse_one_cave_one_more_time() {
        assertThat(new PathWithOneExtraTraversal(ImmutableList.of("start","A","b","A","c","A")).hasTraversed("b"), is(false));
        assertThat(new PathWithOneExtraTraversal(ImmutableList.of("start","A","c","A","b","A")).hasTraversed("c"), is(false));
    }

    @Test
    public void not_traverse_more_than_one_cave_one_more_time() {
        assertThat(new PathWithOneExtraTraversal(ImmutableList.of("start","A","c","A","b","A","b","A")).hasTraversed("c"), is(true));
    }

    @Test
    public void knows_when_it_uses_its_bonus_traversal() {
        Path path = new PathWithOneExtraTraversal(ImmutableList.of("start","A","c","A","b","A"));
        path = path.traverse("b");
        assertThat(path, instanceOf(PathWithOneExtraTraversal.class));
        assertThat(((PathWithOneExtraTraversal)path).getUsedExtraTraversal(), is(true));
    }

    @Test
    public void remembers_it_has_used_its_bonus_traversal_when_traversing_a_large_cave() {
        PathWithOneExtraTraversal path = (PathWithOneExtraTraversal) new PathWithOneExtraTraversal(ImmutableList.of("start")).traverse("A").traverse("b").traverse("A").traverse("b").traverse("A");
        assertThat(path.getUsedExtraTraversal(), is(true));
    }

    @Test
    public void knows_when_it_has_used_its_bonus_traversal() {
        PathWithOneExtraTraversal path = new PathWithOneExtraTraversal(ImmutableList.of("start","A","c","A","b","A", "b"));
        assertThat(path.getUsedExtraTraversal(), is(true));
    }
}
