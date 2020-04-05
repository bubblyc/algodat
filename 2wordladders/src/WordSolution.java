import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class WordSolution {
    private static int nbrOfWords;
    private static int nbrOfQueries;
    private static Node[] words;
    private static Map<Node, Node> queries = new HashMap<>();
    private static Map<Node, List<Node>> neighbours = new HashMap<>();

    // Read from input. First line has two integers N, Q
    // where N = nbr of words and Q = number of queries)
    // Then follows N lines with one five-letter word each.
    // After that comes Q lines containing two spaced-separated five-letter words
    // each. (Ex: hello yello)
    private static void readInFromConsole() {
        Scanner scanner = new Scanner(System.in);
        // Store words in an array
        String[] temp1 = scanner.nextLine().split(" ");
        nbrOfWords = Integer.parseInt(temp1[0]);
        nbrOfQueries = Integer.parseInt(temp1[1]);
        words = new Node[nbrOfWords];
        for (int i = 0; i < nbrOfWords; i++) {
            String word = scanner.nextLine();
            words[i] = new Node(word);

        }
        for (int i = 0; i < nbrOfQueries; i++) {
            String[] temp = new String[2];
            temp = scanner.nextLine().split(" ");
            queries.put(new Node(temp[0]), new Node(temp[1]));
        }
        scanner.close();
    }

    // Constructs a graph with
    private static void constructNeighbours() {
        for(int w = 0; w < nbrOfWords; w++){
            List<Node> listOfNeighbour = new ArrayList<>();
            for(int n = 0; n< nbrOfWords - 1; n++){
                if(containsLetters(words[w].getWord(), words[n].getWord()) && !words[w].equals(words[n])){
                  listOfNeighbour.add(words[n]);
                }
            }
            neighbours.put(words[w], listOfNeighbour);
        }
    }

    private static int findShortestPath(Node startingNode, Node endingNode) {
        if (startingNode.getWord().equals(endingNode.getWord())) {
            return 0;
        } else {
            int lengthOfPath = 1;
            Queue<Node> q = new LinkedList<>();
            Set<Node> visited = new HashSet<>();
            q.add(startingNode);
            visited.add(startingNode);
            while (q.size() != 0) {
                Node v = q.poll();
                List<Node> temp = neighbours.get(v);
                if (temp != null) {
                    for (Node w : temp) {
                        if (!visited.contains(w)) {
                            q.add(w);
                            visited.add(w);
                            w.setPred(v);
                            if (w.equals(endingNode)) {
                                return lengthOfPath;
                            }
                        }
                    }
                }
                lengthOfPath++;
            }
            return -1;
        }
    }

    // Checks if the last 4 letters in startingWord exists in endingWord.
    // Note that order doesn't matter ex hello lolem -> true
    private static boolean containsLetters(String currentWord, String neighbourWord) {
        char[] w1 = currentWord.substring(1).toCharArray();
        String temp = neighbourWord; //wello
        for (int i = 0; i < 4; i ++) {
            char letter = w1[i]; //e
            int index = temp.indexOf(letter);
            if (index == -1) {
                return false;
            } else{
                temp = temp.substring(0, index) + temp.substring(index+1);
            }
        }
        return true;
    }

    // If there exists a path from the starting to ending word, print the length of
    // the shortest path.
    // Otherwise print "Impossible".
    private static void executeNPrintResults() {
        for (Map.Entry<Node, Node> m : queries.entrySet()) {
            int path = findShortestPath(m.getKey(), m.getValue());
            if (path == -1) {
                    System.out.println("Impossible");
            } else {
                System.out.println(path);
            }
        }
    }

    private static void printWords(){
        for (int i = 0; i < words.length; i++) {
            System.out.println(words[i].getWord());
        }
    }

    private static void printQueries() {
        for (Map.Entry<Node, Node> m : queries.entrySet()) {
            System.out.println(m.getKey().getWord() + " " + m.getValue().getWord());
        }
    }

    private static void printNeighbours() {
        for (Map.Entry<Node, List<Node>> n : neighbours.entrySet()) {
            System.out.println(n.getKey().getWord() + ": " );
            for (Node ne: n.getValue()){
                System.out.print(ne.getWord() + " ");
            }
            System.out.println("\n");
            
        }
    }

    public static void main(String[] args) {
        readInFromConsole();
        constructNeighbours();
        executeNPrintResults(); 
    }
}

/*
 * TODO: 
 * - vad gör Pred?? är den nödvändigt, om inte så behöver vi inte Node-klassen 
 * - Titta på tidskomlplexiteten, behöver vi optimera?? 
 * - Fyll i report och labbfrågor.
 */
