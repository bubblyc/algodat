import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    private String word;
    private Node pred;

    Node(String s){
        word=s;
    }

    void setPred(Node v){
        pred = v;
    }

    String getWord() {
        return word; 
    }

    public Node getPred() {
        return pred; 
    }

    Boolean equals(Node node){
        return word.equals(node.getWord());
    }

    
}