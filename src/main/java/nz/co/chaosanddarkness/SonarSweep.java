package nz.co.chaosanddarkness;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;

public class SonarSweep {

    private List<Long> numbers;

    public SonarSweep(InputStream stream) {
        try {
            numbers = IOUtils.readLines(stream, Charset.defaultCharset()).stream().map(Long::parseLong).collect(Collectors.toList());
        }
        catch (IOException e) {
            numbers = List.of();
        }
    }

    public Long countIncreases() {
        Long count = 0L;
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) > numbers.get(i-1)) {
                count ++;
            }
        }
        return count;
    }

    public Long countWindowIncreases() {
        Long count = 0L;
        for (int i = 3; i < numbers.size(); i++) {
            if (sumLast3(i) > sumLast3(i-1)) {
                count ++;
            }
        }
        return count;
    }

    private Long sumLast3(int i) {
        return numbers.get(i-2) + numbers.get(i-1) + numbers.get(i);
    }
}
