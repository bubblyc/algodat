
public class Node {
    private String word;
    private Node pred;

    public Node(String s){
        word=s;
    }

    public void setPred(Node v){
        pred = v;
    }

    public String getWord() {
        return word; 
    }

    public Node getPred() {
        return pred; 
    }
    
}