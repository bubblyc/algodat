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

  
    

    //Read from input. First line has two integers N, Q
    //where N = nbr of words and Q = number of queries)
    //Then follows N lines with one five-letter word each.
    //After that comes Q lines containing two spaced-separated five-letter words each. (Ex: hello yello)
    private static void readInFromConsole() {
        Scanner scanner = new Scanner(System.in);
        //Store words in an array
        String[] temp1 = scanner.nextLine().split(" ");
        nbrOfWords = Integer.parseInt(temp1[0]);
        nbrOfQueries = Integer.parseInt(temp1[1]);
        words = new Node[nbrOfWords];
        for (int i = 0; i < nbrOfWords; i ++){
            String word = scanner.nextLine();
            words[i] = new Node(word);
            
        }
        for (int i = 0; i < nbrOfQueries; i ++) {
            String[] temp = new String[2];
            temp = scanner.nextLine().split(" ");
            queries.put(new Node(temp[0]), new Node(temp[1]));
        }
        scanner.close(); 
    }

    

    //Constructs a graph with
    private void constructGraph(){

    }

    private static void findShortestPath(Node startingNode, Node endingNode) {
        Queue<Node> q = new LinkedList<>(); 
        Set<Node> visited = new HashSet<>(); 
        visited.add(startingNode);
        q.add(startingNode);
        while(q.size() != 0){
            Node v = q.peek();
            for(Node w : neighbours.get(v)){
                if(!visited.contains(w)){
                    visited.add(w);
                    q.add(w);
                    w.setPred(v);
                    if(w.getWord() == endingNode.getWord()){
                        System.out.println("path found");
                        while(w.getWord() != startingNode.getWord()){
                            w = w.getPred();
                            System.out.println(w.getWord());
                        }
                        return;
                    }
                }
            }
            System.out.println("no path found");
            return;
        }
    }

    //Checks if the last 4 letters in startingWord exists in endingWord.
    //Note that order doesn't matter ex hello lolem -> true
    private static boolean containsLetters(String currentWord, String neighbourWord) {
        char[] w1 = currentWord.substring(1).toCharArray();
    
        return false;
    }

    //If there exists a path from the starting to ending word, print the length of the shortest path.
    // Otherwise print "Impossible".
    private void printResults() {
        //TODO
    }

    private static void printOutMap() {
        for (Map.Entry<Node, Node> m : queries.entrySet()) {
            System.out.println(m.getKey().getWord() + " "+ m.getValue().getWord());
        }
    }

    public static void main(String[] args) {
        readInFromConsole();
        //printOutMap();
        Node hello = new Node("hello");
        Node where = new Node("where");
        Node putin = new Node("putin");
        List<Node> list =new ArrayList<Node>();
        list.add(where);
        neighbours.put(hello, list); 
        findShortestPath(hello, where);
        findShortestPath(hello, putin);
    }
}

/*
TODO:
- parsa inläsningen för att skapa grannlistan
- returnera längen på pathen, nu har vi lyckats hitta en path
- vad gör Pred?? är den nödvändigt, om inte så behöver vi inte Node-klassen
- Titta på tidskomlplexiteten, behöver vi optimera?? 
*/
