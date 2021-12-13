package nz.co.chaosanddarkness.syntax;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class ChunkTest {

    @Test
    public void single_brackets_pair_is_valid() {
        assertThat(new Chunk("()").isValid(), is(true));
    }

    @Test
    public void braces_enclosing_brackets_is_valid() {
        assertThat(new Chunk("{()()()}").isValid(), is(true));
    }

    @Test
    public void mismatched_pair_is_invalid() {
        assertThat(new Chunk("(]").isValid(), is(false));
    }
    @Test
    public void mismatched_pair_with_valid_embedded_pairs_is_invalid() {
        assertThat(new Chunk("{()()()>").isValid(), is(false));
    }
}
