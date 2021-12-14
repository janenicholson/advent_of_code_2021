package nz.co.chaosanddarkness.origami;

import lombok.Data;

@Data
public class FoldInstruction {

    public enum InstructionType {
        UP,
        LEFT
    }

    private static final String FOLD_UP = "fold along y=";
    private static final String FOLD_LEFT = "fold along x=";

    private final InstructionType action;
    private final Integer origin;

    public static FoldInstruction parse(String line) {
        if (line.startsWith(FOLD_UP)) {
            return new FoldInstruction(InstructionType.UP, Integer.parseInt(line.substring(FOLD_UP.length())));
        }
        if (line.startsWith(FOLD_LEFT)) {
            return new FoldInstruction(InstructionType.LEFT, Integer.parseInt(line.substring(FOLD_LEFT.length())));
        }
        return null;
    }
}
