import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;
import java.util.HashMap;
import java.util.LinkedList;

public class WordNet {

    private Digraph wordnet;
    private HashMap<String, LinkedList<Integer>> wordMap;
    private HashMap<Integer, String> synetsMap;
    private int numberOfVertices;
    private final SAP sap;
    private boolean root;

    public WordNet(String synsets, String hypernyms) {
        validateNull(synsets, hypernyms);

        constructSynetsSet(synsets);
        constructWordNetDigraph(hypernyms);

        Topological tp = new Topological(wordnet);
        if (!root || !tp.hasOrder()) {
            throw new IllegalArgumentException("Not a rooted DAG");
        }

        sap = new SAP(wordnet);
    }

    private void constructSynetsSet(String synsets) {
        In in = new In(synsets);
        wordMap = new HashMap<>();
        synetsMap = new HashMap<>();

        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            synetsMap.put(numberOfVertices, line[1]);
            String[] synet = line[1].split(" ");
            for (String s : synet) {
                if (wordMap.containsKey(s)) {
                    wordMap.get(s).add(numberOfVertices);
                } else {
                    LinkedList<Integer> idList = new LinkedList<>();
                    idList.add(numberOfVertices);
                    wordMap.put(s, idList);
                }
            }
            numberOfVertices++;
        }
    }

    private void constructWordNetDigraph(String hypernyms) {
        wordnet = new Digraph(numberOfVertices);
        In in = new In(hypernyms);
        int entryCount = 0;

        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            if (line.length > 1) {
                entryCount++;
            }
            int id = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                 wordnet.addEdge(id, Integer.parseInt(line[i]));
            }
        }
        if (entryCount == synetsMap.size() - 1) {
            root = true;
        }
    }

    public Iterable<String> nouns() {
        return wordMap.keySet();
    }

    public boolean isNoun(String word) {
        validateNull(word);

        return wordMap.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        validateNull(nounA, nounB);
        validateWordNet(nounA, nounB);

        Iterable<Integer> v = wordMap.get(nounA);
        Iterable<Integer> w = wordMap.get(nounB);

        return sap.length(v, w);
    }

    public String sap(String nounA, String nounB) {
        validateNull(nounA, nounB);
        validateWordNet(nounA, nounB);

        Iterable<Integer> v = wordMap.get(nounA);
        Iterable<Integer> w = wordMap.get(nounB);
        int ancestor = sap.ancestor(v, w);

        return synetsMap.get(ancestor);
    }

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
        WordNet wn = new WordNet("synsets6.txt", "hypernyms6InvalidTwoRoots.txt");
    }
}
