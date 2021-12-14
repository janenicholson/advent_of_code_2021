package nz.co.chaosanddarkness.path;

public interface Path {

    String current();
    boolean hasTraversed(String cave);
    Path traverse(String cave);
    boolean canTraverse(String cave);
}
