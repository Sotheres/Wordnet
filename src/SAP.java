import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class SAP {

    private final Digraph digraph;
    private int ancestor;

    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("Constructor argument is null");
        }
        digraph = new Digraph(G);
    }

    public int length(int v, int w) {
        validateVertex(v, w);

        BreadthFirstDirectedPaths pathFromV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths pathFromW = new BreadthFirstDirectedPaths(digraph, w);
        int shortest = Integer.MAX_VALUE;
        ancestor = -1;

        for (int i = 0; i < digraph.V(); i++) {
            if (pathFromV.hasPathTo(i) && pathFromW.hasPathTo(i)
                && (pathFromV.distTo(i) + pathFromW.distTo(i) < shortest)) {
                shortest = pathFromV.distTo(i) + pathFromW.distTo(i);
                ancestor = i;
            }
        }

        return shortest != Integer.MAX_VALUE ? shortest : -1;
    }

    public int ancestor(int v, int w) {
        validateVertex(v, w);
        length(v, w);
        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateContainer(v, w);

        BreadthFirstDirectedPaths pathFromV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths pathFromW = new BreadthFirstDirectedPaths(digraph, w);
        int shortest = Integer.MAX_VALUE;
        ancestor = -1;

        for (int i = 0; i < digraph.V(); i++) {
            if (pathFromV.hasPathTo(i) && pathFromW.hasPathTo(i)
                    && (pathFromV.distTo(i) + pathFromW.distTo(i) < shortest)) {
                shortest = pathFromV.distTo(i) + pathFromW.distTo(i);
                ancestor = i;
            }
        }

        return shortest != Integer.MAX_VALUE ? shortest : -1;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateContainer(v, w);
        length(v, w);
        return ancestor;
    }

    private void validateContainer(Iterable<Integer> a, Iterable<Integer> b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        for (Integer i : a) {
            if (i == null) {
                throw new IllegalArgumentException("Null item in the container");
            }
            validateVertex(i);
        }
        for (Integer i : b) {
            if (i == null) {
                throw new IllegalArgumentException("Null item in the container");
            }
            validateVertex(i);
        }
    }

    private void validateVertex(int... a) {
        int v = digraph.V();
        for (int i : a) {
            if (i < 0 || i >= v) {
                throw new IllegalArgumentException("Illegal vertex value");
            }
        }
    }

    public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            System.out.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
