import java.util.List;

public class SonarSweep {

    public Long countIncreases(List<String> text) {
        Long count = 0L;
        for (int i = 1; i < text.size(); i++) {
            if (greaterThanPrevious(text, i)) {
                count ++;
            }
        }
        return count;
    }

    private boolean greaterThanPrevious(List<String> text, int i) {
        return readNth(text, i) > readNth(text, i-1);
    }

    public Long countWindowIncreases(List<String> text) {
        Long count = 0L;
        for (int i = 3; i < text.size(); i++) {
            if (greaterThanLast3(text, i)) {
                count ++;
            }
        }
        return count;
    }

    private boolean greaterThanLast3(List<String> text, int i) {
        return sumLast3(text, i) > sumLast3(text, i-1);
    }

    private Long sumLast3(List<String> text, int i) {
        return readNth(text, i-2) + readNth(text, i-1) + readNth(text, i);
    }

    private Long readNth(List<String> text, int i) {
        return Long.parseLong(text.get(i));
    }
}
