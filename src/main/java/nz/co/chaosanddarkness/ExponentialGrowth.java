package nz.co.chaosanddarkness;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExponentialGrowth {

    private Long[] lanternfishByAge = new Long[9];

    public ExponentialGrowth(InputStream stream) {
        try {
            List<String> lines = IOUtils.readLines(stream, Charset.defaultCharset()).stream().collect(Collectors.toList());
            for (String line : lines) {
                if (!line.isBlank()) {
                    List<Integer> lanternfishAges = List.of(line.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
                    Arrays.setAll(lanternfishByAge, z->0L);
                    for (Integer age: lanternfishAges) {
                        lanternfishByAge[age] += 1;
                    }
                }
            }
        }
        catch (IOException e) {
        }
    }

    public ExponentialGrowth age(int days) {
        if (days == 0) {
            return this;
        }
        Long[] newAges = new Long[9];
        Arrays.setAll(newAges, z->0L);
        for(int i = 1; i <= 8; i++) {
            newAges[i - 1] = lanternfishByAge[i];
        }
        newAges[6] += lanternfishByAge[0];
        newAges[8] += lanternfishByAge[0];
        return new ExponentialGrowth(newAges).age(days-1);
    }

    public long numberOfLanternFish() {
        return List.of(lanternfishByAge).stream().collect(Collectors.summingLong(x->x));
    }
}
