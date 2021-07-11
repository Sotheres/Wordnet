import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class WordNet {

    private Digraph wordnet;
    private HashMap<String, LinkedList<Integer>> wordMap;
    private int numberOfVertices;
    private final SAP sap;
    // TODO: check for rooted DAG
    public WordNet(String synsets, String hypernyms) {
        validateNull(synsets, hypernyms);

        constructSynetsSet(synsets);
        constructWordNetDigraph(hypernyms);
        sap = new SAP(wordnet);
    }

    private void constructSynetsSet(String synsets) {
        In in = new In(synsets);
        wordMap = new HashMap<>();

        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
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

        while(!in.isEmpty()) {
             String[] line = in.readLine().split(",");
             int id = Integer.parseInt(line[0]);
             for (int i = 1; i < line.length; i++) {
                 wordnet.addEdge(id, Integer.parseInt(line[i]));
             }
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

    //TODO: rewrite
    public String sap(String nounA, String nounB) {
        validateNull(nounA, nounB);
        validateWordNet(nounA, nounB);

        Iterable<Integer> v = wordMap.get(nounA);
        Iterable<Integer> w = wordMap.get(nounB);
        int ancestor = sap.ancestor(v, w);
        StringBuilder ancestorSynset = new StringBuilder();

        for (Map.Entry<String, LinkedList<Integer>> entry : wordMap.entrySet()) {
            if (entry.getValue().contains(ancestor)) {
                ancestorSynset.append(entry.getKey()).append(" ");
            }
        }

        return ancestorSynset.toString();
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
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(wn.sap("entity", "edible_fruit"));
        System.out.println(wn.distance("entity", "edible_fruit"));
    }
}
