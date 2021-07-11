import edu.princeton.cs.algs4.In;

public class Outcast {

    private WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        int[] distances = new int[nouns.length];
        int i = 0;
        for (String s : nouns) {
            for (String str : nouns) {
                distances[i] += wordnet.distance(s, str);
            }
            i++;
        }

        int max = distances[0];
        int maxInd = 0;
        for (int k = 0; k < distances.length; k++) {
            if (distances[k] > max) {
                max = distances[k];
                maxInd = k;
            }
        }

        return nouns[maxInd];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            System.out.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
