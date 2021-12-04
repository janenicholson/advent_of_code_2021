package nz.co.chaosanddarkness.bingo;

import com.google.common.annotations.VisibleForTesting;
import java.util.List;
import lombok.Data;

@Data
public class Board {
    private final List<Board.Line> boardLines;
    private final Integer[][] mask;

    public Board(List<Board.Line> boardLines) {
        this.boardLines = boardLines;
        this.mask = new Integer[boardLines.size()][boardLines.size()];
        setMaskToOnes();
    }

    private void setMaskToOnes() {
        for (int i = 0; i < boardLines.size(); i++) {
            for (int j = 0; j < boardLines.size(); j++) {
                mask[i][j] = 1;
            }
        }
    }

    @Data
    public static class Line {
        private final List<Integer> numbers;
    }

    public void strike(Integer number) {
        for (Line line : boardLines) {
            int index = line.getNumbers().indexOf(number);
            if (index >= 0) {
                mask[boardLines.indexOf(line)][index] = 0;
            };
        }
    }

    public boolean bingo() {
        return anyLineIsZeroes() || anyColumnIsZeroes();
    }

    @VisibleForTesting
    boolean anyLineIsZeroes() {
        boolean anyLineIsBingo = false;
        for (int i = 0; i < boardLines.size(); i++) {
            boolean lineIsBingo = true;
            for (int j = 0; j < boardLines.size(); j++) {
                lineIsBingo &= mask[i][j] == 0;
            }
            anyLineIsBingo |= lineIsBingo;
        }
        return anyLineIsBingo;
    }

    @VisibleForTesting
    boolean anyColumnIsZeroes() {
        boolean anyLineIsBingo = false;
        for (int i = 0; i < boardLines.size(); i++) {
            boolean lineIsBingo = true;
            for (int j = 0; j < boardLines.size(); j++) {
                lineIsBingo &= mask[j][i] == 0;
            }
            anyLineIsBingo |= lineIsBingo;
        }
        return anyLineIsBingo;
    }

    Integer remainingNumbers() {
        int sum = 0;
        for (int i = 0; i < boardLines.size(); i++) {
            for (int j = 0; j < boardLines.size(); j++) {
                sum += mask[i][j] * boardLines.get(i).getNumbers().get(j);
            }
        }
        return sum;
    }
}
