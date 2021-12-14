package nz.co.chaosanddarkness.path;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PathWithOneExtraTraversal implements Path {

    private final ImmutableList<String> path;
    private final Boolean usedExtraTraversal;

    public String current() {
        return path.get(path.size() - 1);
    }

    public PathWithOneExtraTraversal(ImmutableList<String> path) {
        this(path, hasDoubleUsedSmallCave(path));
    }

    private static boolean hasDoubleUsedSmallCave(ImmutableList<String> path) {
        List<String> smallCaves = new ArrayList<>();
        for(String cave : path) {
            if (isSmallCave(cave)) {
                if (smallCaves.contains(cave)) {
                    return true;
                }
                smallCaves.add(cave);
            }
        }
        return false;
    }

    private static boolean isSmallCave(String cave) {
        return !isLarge(cave) && !"start".equals(cave) && !"end".equals(cave);
    }

    public Path traverse(String cave) {
        boolean usingExtraTraversal = usedExtraTraversal;
        List<String> existing = new ArrayList<String>(path);
        if (isSmallCave(cave) && path.contains(cave)) {
            usingExtraTraversal = true;
        }
        existing.add(cave);
        return new PathWithOneExtraTraversal(ImmutableList.copyOf(existing), usingExtraTraversal);
    }

    public boolean hasTraversed(String cave) {
        int count = count(cave);
        return count != 0 && (count > 1 || usedExtraTraversal);
    }

    private int count(String cave) {
        int count = 0;
        for (String c : path) {
            if (c.equals(cave)) {
                count++;
            }
        }
        return count;
    }

    public String toString() {
        return Joiner.on(",").join(path);
    }

    public boolean canTraverse(String cave) {
        return !"start".equals(cave) && (isLarge(cave) || !hasTraversed(cave));
    }

    private static boolean isLarge(String cave) {
        return cave.toUpperCase().equals(cave);
    }
}
