import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
// TODO: check corner cases
public class WordNet {
    private Digraph wordnet;
    private HashMap<Integer, String> wordMap;
    private int numberOfVertices;

    public WordNet(String synsets, String hypernyms) {
        validateNull(synsets, hypernyms);

        constructSynetsSet(synsets);
        constructWordNetDigraph(hypernyms);
    }

    private void constructSynetsSet(String synsets) {
        In in = new In(synsets);
        wordMap = new HashMap<>();

        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            String[] synet = line[1].split(" ");
            for (String s : synet) {
                wordMap.put(numberOfVertices++, s);
            }
        }
    }

    private void constructWordNetDigraph(String hypernyms) {
        wordnet = new Digraph(numberOfVertices);
        In in = new In(hypernyms);
        int id;
        String[] line;

        while(!in.isEmpty()) {
             line = in.readLine().split(",");
             id = Integer.parseInt(line[0]);
             for (int i = 1; i < line.length; i++) {
                 wordnet.addEdge(id, Integer.parseInt(line[i]));
             }
        }
    }

//    public Iterable<String> nouns() {
//
//    }

    //TODO: Rewrite the method and check adding one word multiple times
    public boolean isNoun(String word) {
        validateNull(word);

        return wordMap.containsValue(word);
    }

//    public int distance(String nounA, String nounB) {
//        validateNull(nounA, nounB);
//        validateWordNet(nounA, nounB);
//
//
//    }

//    public String sap(String nounA, String nounB) {
//        validateNull(nounA, nounB);
//        validateWordNet(nounA, nounB);
//
//
//    }

    private void validateWordNet(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not a WordNet noun");
        }
    }

    private void validateNull(String... args) {
        for (String arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("Argument is null");
            }
        }
    }

    public static void main(String[] args) {

    }
}
