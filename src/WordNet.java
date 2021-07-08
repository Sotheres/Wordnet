import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.HashSet;

public class WordNet {
    Digraph wordnet;
    HashSet<String> wordSet;

    public WordNet(String synsets, String hypernyms) {
        validate(synsets, hypernyms);

        int size = 0;
        In in = new In(synsets);
        String[] line;
        String[] synet;

        while (!in.isEmpty()) {
            line = in.readLine().split(",");
            synet = line[1].split(" ");
            for (String s : synet) {
                wordSet.add(s);
            }
            size++;
        }

        wordnet = new Digraph(size);
        in = new In(hypernyms);
        int id;

        while(!in.isEmpty()) {
             line = in.readLine().split(",");
             id = Integer.parseInt(line[0]);
             for (int i = 1; i < line.length; i++) {
                 wordnet.addEdge(id, Integer.parseInt(line[i]));
             }
        }
    }

    public Iterable<String> nouns() {

    }

    public boolean isNoun(String word) {
        validate(word);

        return wordSet.contains(word);
    }

    public int distance(String nounA, String nounB) {
        validate(nounA, nounB);
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not a WordNet noun");
        }


    }

    public String sap(String nounA, String nounB) {
        validate(nounA, nounB);
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not a WordNet noun");
        }


    }

    private void validate(String... args) {
        for (String arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("Argument is null");
            }
        }
    }

    public static void main(String[] args) {

    }
}
