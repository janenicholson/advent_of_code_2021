package nz.co.chaosanddarkness.syntax;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;

public class Syntax {

    private final static Map<Character, Integer> score = Map.of(')', 3, '}', 1197, ']', 57, '>', 25137);
    private List<Chunk> chunks;

    public Syntax(InputStream stream) {
        try {
            chunks = IOUtils.readLines(stream, Charset.defaultCharset()).stream().map(Chunk::new).collect(Collectors.toList());
        }
        catch (IOException e) {
            chunks = List.of();
        }
    }

    public List<Boolean> validity() {
        return chunks.stream().map(Chunk::isValid).collect(Collectors.toList());
    }

    public Integer syntaxErrorScore() {
        return chunks.stream().map(Chunk::findInvalidCharacter).filter(Objects::nonNull).map(score::get).collect(Collectors.summingInt(i->i));
    }

    public Long autocompletionScore() {
        List<Long> autocompleteScores = chunks.stream().map(Chunk::unmatchedCharactersScore).filter(Objects::nonNull).sorted().collect(Collectors.toList());
        return autocompleteScores.get(autocompleteScores.size() / 2);
    }
}
