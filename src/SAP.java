import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    Digraph digraph;

    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("Constructor argument is null");
        }

        digraph = new Digraph(G);

    }

    public int length(int v, int w) {
        validateVertex(v, w);

        BreadthFirstDirectedPaths paths = new BreadthFirstDirectedPaths(digraph, v);
        if (paths.hasPathTo(w)) {
            return paths.distTo(w);
        } else {
            return -1;
        }
    }

    public int ancestor(int v, int w) {
        validateVertex(v, w);


    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v, w);


    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v, w);


    }

    private void validate(Iterable<Integer> a, Iterable<Integer> b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Argument is null");
        }
    }

    private void validateVertex(int a, int b) {
        int v = digraph.V();
        if (a < 0 || a >= v || b < 0 || b >= v) {
            throw new IllegalArgumentException("Illegal vertex value");
        }
    }

    public static void main(String[] args) {

    }
}
