import java.util.LinkedList;
import java.util.Scanner;

public class FriendSolution {
    static Integer N;
    static Integer M;
    static Graph graph;

    static void readIn() {
        Scanner scanner = new Scanner(System.in);
        String[] temp1 = scanner.nextLine().split(" ");
        N = Integer.parseInt(temp1[0]);
        M = Integer.parseInt(temp1[1]);
        graph = new Graph(N);
        while (scanner.hasNextLine()) {
            String[] temp2 = scanner.nextLine().split(" ");
            System.out.println(temp2[0] + temp2[1] + temp2[2]);
            Vertex n1 = new Vertex(Integer.parseInt(temp2[0]));
            Vertex n2 = new Vertex(Integer.parseInt(temp2[1]));
            int w = Integer.parseInt(temp2[2]);
            graph.addEdge(n1, n2, w);
        }
        scanner.close();
    }

    public void prim() {

    }

    public static void main(String[] args) {
        readIn();
        graph.printGraph();
    }

}

class Graph {
    int nbrOfN;
    LinkedList<Edge>[] adjList;

    public Graph(int n) {
        nbrOfN = n;
        adjList = new LinkedList[nbrOfN + 1];
        for (int i = 1; i < nbrOfN + 1; i++) {
            adjList[i] = new LinkedList<Edge>();
        }
    }

    public void addEdge(Vertex n1, Vertex n2, int w) {
        Edge edgeOneWay = new Edge(n1.getName(), n2.getName(), w);
        adjList[n1.getName()].addFirst(edgeOneWay);
        Edge edgeReturn = new Edge(n2.getName(), n1.getName(), w);
        adjList[n2.getName()].addFirst(edgeReturn);
    }

    public void printGraph() {
        for (int i = 1; i <= nbrOfN; i++) {
            LinkedList<Edge> list = adjList[i];
            for (int j = 0; j < list.size(); j++) {
                System.out.println(
                        "vertex-" + i + " is connected to " + list.get(j).n2 + " with weight " + list.get(j).weight);
            }
        }
    }

}

class Edge {
    int n1;
    int n2;
    int weight;

    Edge(int n1, int n2, int weight) {
        this.n1 = n1;
        this.n2 = n2;
        this.weight = weight;
    }
}

class Vertex {
    private Vertex prev = null;
    private int name;
    private Boolean isVisited = false;

    Vertex(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public boolean isVisited(){
        return isVisited;
    }
    public void setVisited() {
        isVisited = true;
    }

    public int getPrev() {
        return prev.getName();
    }

}