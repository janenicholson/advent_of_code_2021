package nz.co.chaosanddarkness.path;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import lombok.Data;
import org.apache.commons.io.IOUtils;

@Data
public class CaveConnections {

    Multimap<String, String> connections;
    PathFactory pathFactory = new PathFactory();

    public CaveConnections(InputStream stream) {
        try {
            connections = IOUtils.readLines(stream, Charset.defaultCharset()).stream()
                    .map(s->s.split("-"))
                    .collect(new MultimapAccumulator<String[], String, String>(s->s[0], s->s[1]));
            Multimap<String, String> reverseRoutes = ArrayListMultimap.create();
            for (Map.Entry<String, String> connections: connections.entries()) {
                reverseRoutes.put(connections.getValue(), connections.getKey());
            }
            connections.putAll(reverseRoutes);
        }
        catch (IOException e) {
            connections = ImmutableMultimap.of();
        }
    }

    public List<String> uniquePaths() {
        return uniquePaths(pathFactory.createSingleTraversalPath());
    }

    public List<String> uniquePathsAllowingOneExtraVisit() {
        return uniquePaths(pathFactory.createOneExtraTraversalPath());
    }

    public List<String> uniquePaths(Path initial) {
        List<String> paths = new ArrayList<>();
        Stack<Path> traversed = new Stack<>();
        traversed.add(initial);
        while (!traversed.isEmpty()) {
            Path path = traversed.pop();
            Collection<String> connectedCaves = connections.get(path.current());
            for (String cave: connectedCaves) {
                if ("end".equals(cave)) {
                    paths.add(path.traverse(cave).toString());
                }
                else if (path.canTraverse(cave)) {
                    traversed.add(path.traverse(cave));
                }
            }
        }
        return paths;
    }
}
