public class WordNet {
    public WordNet(String synsets, String hypernyms) {
        validate(synsets, hypernyms);
    }

    public Iterable<String> nouns() {

    }

    public boolean isNoun(String word) {
        validate(word);
    }

    public int distance(String nounA, String nounB) {
        validate(nounA, nounB);
    }

    public String sap(String nounA, String nounB) {
        validate(nounA, nounB);
    }

    public static void main(String[] args) {

    }

    private void validate(String... args) {
        for (String arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("Argument is null");
            }
        }
    }
}
