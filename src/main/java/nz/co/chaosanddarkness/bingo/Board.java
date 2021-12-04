package nz.co.chaosanddarkness.bingo;

import com.google.common.annotations.VisibleForTesting;
import lombok.Data;

@Data
public class Board {
    private final Integer[][] boardLines;
    private final Integer[][] mask;

    public Board(Integer[][] boardLines) {
        this.boardLines = boardLines;
        this.mask = new Integer[boardLines.length][boardLines.length];
        setMaskToOnes();
    }

    private void setMaskToOnes() {
        for (int i = 0; i < boardLines.length; i++) {
            for (int j = 0; j < boardLines.length; j++) {
                mask[i][j] = 1;
            }
        }
    }

    public void strike(Integer number) {
        for (int i = 0; i < boardLines.length; i++) {
            for (int j = 0; j < boardLines.length; j++) {
                if (boardLines[i][j] == number) {
                    mask[i][j] = 0;
                    return;
                }
            }
        }
    }

    public boolean bingo() {
        return anyLineIsZeroes() || anyColumnIsZeroes();
    }

    @VisibleForTesting
    boolean anyLineIsZeroes() {
        boolean anyLineIsBingo = false;
        for (int i = 0; i < boardLines.length; i++) {
            boolean lineIsBingo = true;
            for (int j = 0; j < boardLines.length; j++) {
                lineIsBingo &= mask[i][j] == 0;
            }
            anyLineIsBingo |= lineIsBingo;
        }
        return anyLineIsBingo;
    }

    @VisibleForTesting
    boolean anyColumnIsZeroes() {
        boolean anyLineIsBingo = false;
        for (int i = 0; i < boardLines.length; i++) {
            boolean lineIsBingo = true;
            for (int j = 0; j < boardLines.length; j++) {
                lineIsBingo &= mask[j][i] == 0;
            }
            anyLineIsBingo |= lineIsBingo;
        }
        return anyLineIsBingo;
    }

    Integer remainingNumbers() {
        int sum = 0;
        for (int i = 0; i < boardLines.length; i++) {
            for (int j = 0; j < boardLines.length; j++) {
                sum += mask[i][j] * boardLines[i][j];
            }
        }
        return sum;
    }
}
