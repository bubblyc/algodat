import java.util.Scanner;

public class WordSolution {
    private int nbrOfWords;
    private int nbrOfQueries;
    private String[] words;

    //Read from input. First line has two integers N, Q
    //where N = nbr of words and Q = number of queries)
    //Then follows N lines with one five-letter word each.
    //After that comes Q lines containing two spaced-separated five-letter words each. (Ex: hello yello)
    private void readInFromConsole() {
        //TODO
        Scanner scanner = new Scanner(System.in);
        //Store words in an array
        words = new String[nbrOfWords];

    }

    //Constructs a graph with
    private void constructGraph() {

    }

    private int findShortestPath(String startingFrom, String endingAt) {
        return 0;
    }

    //Checks if the last 4 letters in startingWord exists in endingWord.
    //Note that order doesn't matter ex hello lolem -> true
    private static boolean containsLetters(String startingWord, String endingWord) {
        String w1 = startingWord.substring(1);
        return false;
    }

    //If there exists a path from the starting to ending word, print the length of the shortest path.
    // Otherwise print "Impossible".
    private void printResults() {
        //TODO
    }

    public static void main(String[] args) {
    }
}
