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
    private static Node[] words;
    private static Node[] query;
    private static Map<String, List<Node>> neighbours = new HashMap<>();

    // Read from input.
    private static void readInFromConsole() {
        Scanner scanner = new Scanner(System.in);
        String[] temp1 = scanner.nextLine().split(" ");
        //number of words and number of queries
        nbrOfWords = Integer.parseInt(temp1[0]);
        int nbrOfQueries = Integer.parseInt(temp1[1]);
        //Put all words in an array
        words = new Node[nbrOfWords];
        for (int i = 0; i < nbrOfWords; i++) {
            String word = scanner.nextLine();
            words[i] = new Node(word);
        }
        //Put all queries in an array
        query = new Node[nbrOfQueries * 2];
        for (int i = 0; i < nbrOfQueries * 2 - 1; i += 2) {
            String [] temp = scanner.nextLine().split(" ");
            query[i] = new Node(temp[0]);
            query[i + 1] = new Node(temp[1]);
        }
        scanner.close();
    }

    //For each word in words[] compare current word with the rest of the words and store incident nodes in a list.
    //Store each word with its list of neighbours in a Map.
    private static void constructNeighbours() {
        for (int w = 0; w < nbrOfWords; w++) {
            List<Node> listOfNeighbour = new ArrayList<>();
            for (int n = 0; n < nbrOfWords; n++) {
                if (containsLetters(words[w].getWord(), words[n].getWord()) && !words[w].equals(words[n])) {
                    listOfNeighbour.add(words[n]);
                }
            }
            neighbours.put(words[w].getWord(), listOfNeighbour);
        }
    }

    private static int findShortestPath(Node startingNode, Node endingNode) {
        //Return 0 if startingNode == endingNode
        if (startingNode.getWord().equals(endingNode.getWord())) {
            return 0;
        }
        //Add startingNode to an empty queue and add it to visited.
        else {
            int lengthOfPath = 1;
            Queue<Node> q = new LinkedList<>();
            Set<Node> visited = new HashSet<>();
            q.add(startingNode);
            visited.add(startingNode);
            //If queue is not empty take out first element and get its neighbours
            while (q.size() != 0) {
                Node v = q.poll();
                List<Node> temp = neighbours.get(v.getWord());
                //If word has neighbours
                if (temp != null) {
                    //For each word in neighbours, if we haven't visited the node before add node to q.
                    //Set the pred node to pred.
                    for (Node w : temp) {
                        if (!visited.contains(w)) {
                            q.add(w);
                            visited.add(w);
                            w.setPred(v);
                            //If we have found the node we were looking for return lengthOfPath
                            if (w.equals(endingNode)) {
                                Node tempNode = w.getPred();
                                while (!tempNode.equals(startingNode)) {
                                    lengthOfPath++;
                                    tempNode = tempNode.getPred();
                                }
                                return lengthOfPath;
                            }
                        }
                    }
                }
            }
            return -1;
        }
    }

    // Checks if the last 4 letters in startingWord exists in endingWord.
    private static boolean containsLetters(String currentWord, String neighbourWord) {
        //Last four letters
        char[] w1 = currentWord.substring(1).toCharArray();
        String temp = neighbourWord;
        //Checks for each letter if neighbourWord contains that letter. If not return false.
        //Else remove the letter from currentWord. If every letter exists return true.
        for (int i = 0; i < 4; i++) {
            char letter = w1[i];
            int index = temp.indexOf(letter);
            if (index == -1) {
                return false;
            }
            else {
                temp = temp.substring(0, index) + temp.substring(index + 1);
            }
        }
        return true;
    }

    // If there exists a path from the starting to ending word, print the length of the shortest path.
    // Otherwise print "Impossible".
    private static void executeNPrintResults() {
        for (int i = 0; i < query.length - 1; i += 2) {
            int path = findShortestPath(query[i], query[i + 1]);
            if (path == -1) {
                System.out.println("Impossible");
            }
            else {
                System.out.println(path);
            }
        }
    }

    private static void printWords() {
        for (Node word : words) {
            System.out.println(word.getWord());
        }
    }

    private static void printNeighbours() {
        for (Map.Entry<String, List<Node>> n : neighbours.entrySet()) {
            System.out.println(n.getKey() + ": ");
            for (Node ne : n.getValue()) {
                System.out.print(ne.getWord() + " ");
            }
            System.out.println("\n");

        }
    }

    public static void main(String[] args) {
        //double t1 = System.currentTimeMillis();
        readInFromConsole();
        //double t2 = System.currentTimeMillis();
        constructNeighbours();
        //double t3 = System.currentTimeMillis();
        executeNPrintResults();
        //double t4 = System.currentTimeMillis();

        /*System.out.println("Reading took: " + (t2 - t1) / 1000 + " seconds");
        System.out.println("Constructing neighbours took: " + (t3 - t2) / 1000 + " seconds");
        System.out.println("Algorithm took " + (t4 - t3) / 1000 + " seconds");
        System.out.println("Total: " + (t4 - t1) / 1000 + " seconds");*/
    }
}

