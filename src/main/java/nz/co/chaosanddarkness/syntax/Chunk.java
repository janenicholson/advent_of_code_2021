package nz.co.chaosanddarkness.syntax;

import java.util.Map;
import java.util.Stack;
import lombok.Data;

@Data
public class Chunk {

    private final static Map<Character, Character> matched = Map.of(')', '(', '}', '{', ']', '[', '>', '<');
    private final String value;

    public boolean isValid() {
        return findInvalidCharacter() == null;
    }

    public Character findInvalidCharacter() {
        Stack<Character> parsed = new Stack<>();
        for (Character c : value.toCharArray()) {
            if (opener(c)) {
                parsed.add(c);
            }
            else {
                if (matched.get(c).equals(parsed.peek())) {
                    parsed.pop();
                }
                else {
                    return c;
                }
            }
        }
        return null;
    }

    private Stack<Character> findUnmatchedCharacters() {
        Stack<Character> parsed = new Stack<>();
        for (Character c : value.toCharArray()) {
            if (opener(c)) {
                parsed.add(c);
            }
            else {
                if (matched.get(c).equals(parsed.peek())) {
                    parsed.pop();
                }
                else {
                    return null;
                }
            }
        }
        return parsed;
    }

    private final static Map<Character, Integer> score = Map.of('(', 1, '[', 2, '{', 3, '<', 4);

    public Long unmatchedCharactersScore() {
        return score(findUnmatchedCharacters());
    }

    private Long score(Stack<Character> unmatched) {
        if (unmatched == null) {
            return null;
        }
        long runningScore = 0;
        while (!unmatched.isEmpty()) {
            runningScore = runningScore * 5 + score.get(unmatched.pop());
        }
        return runningScore;
    }

    private boolean opener(Character c) {
        return matched.values().contains(c);
    }
}
