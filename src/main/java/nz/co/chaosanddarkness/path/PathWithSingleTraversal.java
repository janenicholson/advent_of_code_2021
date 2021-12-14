package nz.co.chaosanddarkness.path;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PathWithSingleTraversal implements Path {

    private final ImmutableList<String> path;

    public String current() {
        return path.get(path.size() - 1);
    }

    public Path traverse(String cave) {
        List<String> existing = new ArrayList<String>(path);
        existing.add(cave);
        return new PathWithSingleTraversal(ImmutableList.copyOf(existing));
    }

    public boolean hasTraversed(String cave) {
        return path.contains(cave);
    }

    public String toString() {
        return Joiner.on(",").join(path);
    }

    public boolean canTraverse(String cave) {
        return !"start".equals(cave) && (isLarge(cave) || !hasTraversed(cave));
    }

    private boolean isLarge(String connection) {
        return connection.toUpperCase().equals(connection);
    }
}
