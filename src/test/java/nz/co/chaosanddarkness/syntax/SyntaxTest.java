package nz.co.chaosanddarkness.syntax;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SyntaxTest {

    @Test
    public void syntax_error_score_is_26397() {
        assertThat(construct("navigation.txt").syntaxErrorScore(), is(26397));
    }

    @Test
    public void syntax_error_score_is_unknown() {
        assertThat(construct("navigation_full.txt").syntaxErrorScore(), is(388713));
    }

    @Test
    public void autocompletion_score_is_288957() {
        assertThat(construct("navigation.txt").autocompletionScore(), is(288957L));
    }

    @Test
    public void autocompletion_score_is_unknown() {
        assertThat(construct("navigation_full.txt").autocompletionScore(), is(3539961434L));
    }

    public Syntax construct(String resource) {
        return new Syntax(SyntaxTest.class.getResourceAsStream(resource));
    }

}
