package nz.co.chaosanddarkness.bingo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.List;
import org.junit.Test;

public class BingoTest {

    @Test
    public void load_bingo_run() throws IOException {
        Bingo bingo = new Bingo(BingoTest.class.getResourceAsStream("bingo.txt"));
        assertThat(bingo.getRun().getNumbers(), contains(7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1));
    }

    @Test
    public void skip_blank_lines() {
        List<String> input = List.of("", " ", "x", "y", " ", "");
        assertThat(Bingo.skipEmptyLines(input), contains("x", "y", " ", ""));
    }

    @Test
    public void lines_until_empty() {
        List<String> input = List.of("x", "y", " ", "");
        assertThat(Bingo.linesUntilEmpty(input), contains("x", "y"));
    }

    @Test
    public void split_line_into_integers() {
        String line = "22 13 17 11  0";
        assertThat(Bingo.boardLine(line).getNumbers(), contains(22,13,17,11,0));
    }

    @Test
    public void load_bingo_boards() throws IOException {
        Bingo bingo = new Bingo(BingoTest.class.getResourceAsStream("bingo.txt"));
        assertThat(bingo.getBoards(), hasSize(3));
        assertThat(bingo.getBoards().get(0).getBoardLines(), hasSize(5));
        assertThat(bingo.getBoards().get(0).getBoardLines().get(0).getNumbers(), contains(22,13,17,11,0));
        assertThat(bingo.getBoards().get(2).getBoardLines(), hasSize(5));
        assertThat(bingo.getBoards().get(2).getBoardLines().get(4).getNumbers(), contains(2,0,12,3,7));
    }

    @Test
    public void winning_board_score_is_4512() {
        Bingo bingo = new Bingo(BingoTest.class.getResourceAsStream("bingo.txt"));
        bingo.play();
        assertThat(bingo.lastNumberCalled(), is(24));
        assertThat(bingo.winningBoardRemainingNumbersTotal(), is(188));
        assertThat(bingo.winningScore(), is(4512));
        assertThat(bingo.playToLose(), is(1924));
    }

    @Test
    public void winning_board_score_is_unknown() {
        Bingo bingo = new Bingo(BingoTest.class.getResourceAsStream("bingo_full.txt"));
        bingo.play();
        assertThat(bingo.winningScore(), is(28082));
        assertThat(bingo.playToLose(), is(8224));
    }

}
