package nz.co.chaosanddarkness.origami;

import static nz.co.chaosanddarkness.origami.FoldInstruction.InstructionType.LEFT;
import static nz.co.chaosanddarkness.origami.FoldInstruction.InstructionType.UP;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

import java.util.List;
import org.junit.Test;

public class OrigamiTest {

    @Test
    public void can_parse_dots() {
        Origami origami = construct("transparent_paper.txt");
        assertThat(origami.getDots(), arrayWithSize(15));
        assertThat(origami.getDots()[0], arrayWithSize(11));
    }

    @Test
    public void can_print() {
        Origami origami = construct("transparent_paper.txt");
        String[] dots = origami.printableDots();
        assertThat(dots[0], is("...#..#..#."));
        assertThat(dots[14], is("#.#........"));
    }

    @Test
    public void can_parse_instructions() {
        Origami origami = construct("transparent_paper.txt");
        List<FoldInstruction> instructions = origami.getInstructions();
        assertThat(instructions.size(), is(2));
        assertThat(instructions.get(0), is(new FoldInstruction(UP, 7)));
        assertThat(instructions.get(1), is(new FoldInstruction(LEFT, 5)));
    }

    @Test
    public void can_fold_up() {
        Origami origami = construct("transparent_paper.txt").fold();
        List<FoldInstruction> instructions = origami.getInstructions();
        assertThat(instructions, contains(new FoldInstruction(LEFT, 5)));
        String[] dots = origami.printableDots();
        assertThat(dots.length, is(7));
        assertThat(dots[0], is("#.##..#..#."));
        assertThat(dots[6], is("..........."));
        assertThat(origami.countDots(), is(17));
    }

    @Test
    public void can_fold_left() {
        Origami origami = construct("transparent_paper.txt").fold().fold();
        List<FoldInstruction> instructions = origami.getInstructions();
        assertThat(instructions, empty());
        String[] dots = origami.printableDots();
        assertThat(dots.length, is(7));
        assertThat(dots[0], is("#####"));
        assertThat(dots[6], is("....."));
        assertThat(origami.countDots(), is(16));
    }

    @Test
    public void can_fold_all() {
        Origami origami = construct("transparent_paper.txt").foldAll();
        List<FoldInstruction> instructions = origami.getInstructions();
        assertThat(instructions, empty());
        String[] dots = origami.printableDots();
        assertThat(dots.length, is(7));
        assertThat(dots[0], is("#####"));
        assertThat(dots[6], is("....."));
        assertThat(origami.countDots(), is(16));
    }

    @Test
    public void can_load_full() {
        Origami origami = construct("transparent_paper_full.txt");
        assertThat(origami.printableDots().length, is(895));
        assertThat(origami.printableDots()[0].length(), is(1311));
    }

    @Test
    public void can_fold_full() {
        Origami origami = construct("transparent_paper_full.txt").fold();
        List<FoldInstruction> instructions = origami.getInstructions();
        assertThat(instructions.get(0), is(new FoldInstruction(UP, 447)));
        String[] dots = origami.printableDots();
        assertThat(dots.length, is(895));
        assertThat(dots[0].length(), is(655));
        assertThat(origami.countDots(), is(753));
    }

    @Test
    public void can_fold_all_full() {
        Origami origami = construct("transparent_paper_full.txt").foldAll();
        List<FoldInstruction> instructions = origami.getInstructions();
        assertThat(instructions, empty());
        String[] dots = origami.printableDots();
        assertThat(dots.length, is(6));
        assertThat(dots, arrayContaining(
                "#..#.####.#....####.#..#...##.###..#..#.",
                "#..#....#.#....#....#..#....#.#..#.#.#..",
                "####...#..#....###..####....#.#..#.##...",
                "#..#..#...#....#....#..#....#.###..#.#..",
                "#..#.#....#....#....#..#.#..#.#.#..#.#..",
                "#..#.####.####.####.#..#..##..#..#.#..#."
                ));
    }

    private Origami construct(String resource) {
        return new Origami(OrigamiTest.class.getResourceAsStream(resource));
    }

}
