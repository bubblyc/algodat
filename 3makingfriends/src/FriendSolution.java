import java.util.*;

public class FriendSolution {
    private static Integer nbrOfNodes;
    private static Graph graph;
    private static PriorityQueue<Edge> pq;
    private static boolean[] visited;
    private static int minCostSum;

    static void readIn() {
        Scanner scanner = new Scanner(System.in);
        String[] temp1 = scanner.nextLine().split(" ");
        nbrOfNodes = Integer.parseInt(temp1[0]);
        graph = new Graph(nbrOfNodes);
        while (scanner.hasNextLine()) {
            String[] temp2 = scanner.nextLine().split(" ");
            // - 1 so that the "naming" of the nodes are more compatible with array indices (start from 0 and not 1)
            int source = Integer.parseInt(temp2[0]) - 1;
            int dest = Integer.parseInt(temp2[1]) - 1;
            int weight = Integer.parseInt(temp2[2]);
            graph.addUndirectedEdge(source, dest, weight);
        }
        scanner.close();
    }

    public static void solvePrimsMST() {
        //Priority queue sort the edges such that the edge with less weight is more prioritized.
        //It does so continuosly as elements are added to the list.
        pq = new PriorityQueue<Edge>();

        //Keeps track of if the node has already been visisted
        visited = new boolean[nbrOfNodes];

        // Add initial set of edges from node 0.
        addEdges(0);

        while (!pq.isEmpty()) {
            //Edge of least weight will be polled thanks to priority queue.
            Edge edge = pq.poll();
            //The next node will be the current nodes destination.
            int nextNode = edge.destination;
            //If the next node hasn't been visited yet add the edge weight to minCostSum.
            if (!visited[nextNode]) {
                minCostSum += edge.weight;
                addEdges(nextNode);
            }
        }

    }

    private static void addEdges(int node) {
        //Set the node index to true so that it is clear that it has already been visited to avoid cycles in MST.
        visited[node] = true;
        //Take out all the edges that are connected to this node
        List<Edge> edges = graph.adjList[node];
        //Check so that the destination nodes haven't been visited yet.
        //Add all edges to unvisited destination nodes to pq.
        for (Edge edge : edges) {
            if (!visited[edge.destination]) {
                pq.offer(edge);
            }
        }
    }


    public static void main(String[] args) {
        readIn();
        solvePrimsMST();
        System.out.println(minCostSum);
    }
}

class Graph {
    int nbrOfNodes;
    //Array of LinkedList
    LinkedList<Edge>[] adjList;

    public Graph(int n) {
        nbrOfNodes = n;
        adjList = new LinkedList[nbrOfNodes];
        //Initialize adjList
        for (int i = 0; i < nbrOfNodes; i++) {
            adjList[i] = new LinkedList<Edge>();
        }
    }

    public void addUndirectedEdge(int source, int dest, int weight) {
        adjList[source].addFirst(new Edge(source, dest, weight));
        adjList[dest].addFirst(new Edge(dest, source, weight));
    }
/*
    public void printGraph() {
        for (int i = 1; i < nbrOfN; i++) {
            LinkedList<Edge> list = adjList[i];
            for (Edge edge : list) {
                System.out.println(
                        "vertex " + i + " is connected to " + edge.destination + " with weight " + edge.weight);
            }
        }
    }

 */
}

//Override compareTo method makes it possible to use in PriorityQueue such that the edge with least weight is prioritized.
class Edge implements Comparable<Edge> {
    int source;
    int destination;
    int weight;

    Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return weight - other.weight;
    }
}
