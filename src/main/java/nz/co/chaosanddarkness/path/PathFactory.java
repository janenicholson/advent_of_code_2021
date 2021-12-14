package nz.co.chaosanddarkness.path;

import com.google.common.collect.ImmutableList;

public class PathFactory {

    public Path createSingleTraversalPath() {
        return new PathWithSingleTraversal(ImmutableList.of("start"));
    }

    public Path createOneExtraTraversalPath() {
        return new PathWithOneExtraTraversal(ImmutableList.of("start"));
    }
}
