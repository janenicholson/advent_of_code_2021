package nz.co.chaosanddarkness.bingo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class BoardTest {

    @Test
    public void no_bingo_when_all_zeroes() {
        Board board = constructBoard();
        assertThat(board.bingo(), is(false));
    }

    @Test
    public void bingo_when_last_line_bingo() {
        Board board = constructBoard();
        board.strike(21);
        board.strike(22);
        board.strike(23);
        board.strike(24);
        board.strike(25);
        assertThat(board.bingo(), is(true));
    }

    @Test
    public void bingo_when_last_column_bingo() {
        Board board = constructBoard();
        board.strike(5);
        board.strike(10);
        board.strike(15);
        board.strike(20);
        board.strike(25);
        assertThat(board.bingo(), is(true));
    }

    private Board constructBoard() {
        return new Board(new Integer[][] {
                new Integer[]{1, 2, 3, 4, 5},
                new Integer[]{6, 7, 8, 9, 10},
                new Integer[]{11, 12, 13, 14, 15},
                new Integer[]{16, 17, 18, 19, 20},
                new Integer[]{21, 22, 23, 24, 25}
        });
    }

}
