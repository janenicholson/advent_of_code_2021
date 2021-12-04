package nz.co.chaosanddarkness.bingo;

import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.io.IOUtils;

@Data
public class Bingo {

    private BingoRun run;
    private Board[] boards;
    private Integer lastNumberCalled;

    public Bingo(InputStream stream) {
        List<String> lines;
        try {
            lines = IOUtils.readLines(stream, Charset.defaultCharset());
            run = run(lines);
            boards = boards(lines.subList(1, lines.size()));
        }
        catch (IOException e) {
            run = null;
            boards = null;
        }
    }

    @VisibleForTesting
    static BingoRun run(List<String> lines) {
        return new BingoRun(List.of(lines.get(0).split(",")).stream().map(Integer::parseInt).collect(Collectors.toList()));
    }

    @VisibleForTesting
    static Board[] boards(List<String> lines) {
        List<Board> boards = new ArrayList<>();
        List<String> boardLines = null;
        while (lines.size() > 0) {
            lines = skipEmptyLines(lines);
            boardLines = linesUntilEmpty(lines);
            boards.add(board(boardLines));
            lines = lines.subList(boardLines.size(), lines.size());
        }
        return boards.toArray(new Board[]{});
    }

    @VisibleForTesting
    static Board board(List<String> lines) {
        return new Board(lines.stream().map(Bingo::boardLine).collect(Collectors.toList()).toArray(new Integer[][]{}));
    }

    @VisibleForTesting
    static Integer[] boardLine(String line) {
        return List.of(line.trim().split("\\s+")).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList()).toArray(new Integer[]{});
    }

    @VisibleForTesting
    static List<String> skipEmptyLines(List<String> lines) {
        int index = 0;
        while (lines.get(index).isBlank()) {
            index++;
        }
        return lines.subList(index, lines.size());
    }

    @VisibleForTesting
    static List<String> linesUntilEmpty(List<String> lines) {
        List<String> nonblankLines = new ArrayList<>();
        for (String line : lines) {
            if (line.isBlank()) {
                return nonblankLines;
            }
            nonblankLines.add(line);
        }
        return nonblankLines;
    }

    @Data
    public static class BingoRun {
        private final List<Integer> numbers;
    }

    public Integer lastNumberCalled() {
        return lastNumberCalled;
    }

    public Integer winningBoardRemainingNumbersTotal() {
        return List.of(boards).stream().filter(Board::bingo).mapToInt(Board::remainingNumbers).sum();
    }

    public Integer winningScore() {
        return lastNumberCalled() * winningBoardRemainingNumbersTotal();
    }

    public void play() {
        for (Integer number : run.getNumbers()) {
            for(Board board : boards) {
                board.strike(number);
                if (board.bingo()) {
                    lastNumberCalled = number;
                    return;
                }
            }
        }
    }

    public Integer playToLose() {
        Integer[] lastNumberCalled = new Integer[boards.length];
        Integer[] numberOfStrikes = new Integer[boards.length];
        for (int i = 0; i < boards.length; i ++) {
            lastNumberCalled[i] = 0;
            numberOfStrikes[i] = 0;
            Board board = boards[i];
            for (Integer number : run.getNumbers()) {
                board.strike(number);
                if (board.bingo()) {
                    lastNumberCalled[i] = number;
                    numberOfStrikes[i] = run.getNumbers().indexOf(number);
                    break;
                }
            }
        }
        int loserIndex = -1;
        for (int i = 0; i < boards.length; i++) {
            if (loserIndex == -1 || numberOfStrikes[i] > numberOfStrikes[loserIndex]) {
                loserIndex = i;
            }
        }
        return lastNumberCalled[loserIndex] * boards[loserIndex].remainingNumbers();
    }
}
