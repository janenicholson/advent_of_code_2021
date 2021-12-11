package nz.co.chaosanddarkness.segments;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import lombok.Data;

@Data
public class Signals {
    private final List<SignalLine> signals = new ArrayList<>();

    public Signals(InputStream stream) {
        try {
        List<String> lines = IOUtils.readLines(stream, Charset.defaultCharset()).stream().collect(Collectors.toList());
        for (String line : lines) {
            if (!line.isBlank()) {
                signals.add(SignalLine.parse(line.split("\\|")));
            }
        }
        } catch (IOException e) {}
    }

    public long countDistinguishableDigits() {
        return signals.stream().map(SignalLine::getDigits).flatMap(List::stream).filter(x->Segments.VALID_COMBINATIONS.get(x.length()).size() == 1).count();
    }

    public List<Integer> getDigits() {
        return signals.stream().map(SignalLine::decodedDigits).collect(Collectors.toList());
    }

    public Integer getDigitSum() {
        return signals.stream().map(SignalLine::decodedDigits).collect(Collectors.summingInt(x->x));
    }
}
