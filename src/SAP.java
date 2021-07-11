import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class SAP {

    private final Digraph digraph;
    private int ancestor;
    private int vSize;
    private int wSize;


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

    private void validateVertex(int... a) {
        int v = digraph.V();
        for (int i : a) {
            if (i < 0 || i >= v) {
                throw new IllegalArgumentException("Illegal vertex value");
            }
        }
    }

    public int ancestor(int v, int w) {
        validateVertex(v, w);
        length(v, w);
        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateNullContainer(v, w);
        if (validateZeroLengthArg()) {
            return  -1;
        }

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

    private void validateNullContainer(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        vSize = 0;
        for (Integer i : v) {
            if (i == null) {
                throw new IllegalArgumentException("Null item in the container");
            }
            vSize++;
            validateVertex(i);
        }
        wSize = 0;
        for (Integer i : w) {
            if (i == null) {
                throw new IllegalArgumentException("Null item in the container");
            }
            wSize++;
            validateVertex(i);
        }
    }

    private boolean validateZeroLengthArg() {
        return vSize == 0 || wSize == 0;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateNullContainer(v, w);
        length(v, w);
        return ancestor;
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
