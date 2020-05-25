import java.util.*;

import static java.lang.Math.min;

public class RailwaySolution {

    static List<Edge>[] residualGraph;
    static List<Edge>[] graphCopy;
    static Edge[] graph;
    static int[] routesToRemove;
    static Edge[] edgesToRemove;
    static Queue<Edge> edgesToRemoveCopy;
    static int nodes, edges, students, routes;
    static int s, t;
    private static int maxFlow;
    private static int visitedIdentifier = 0;
    private static int[] visited;

    public static void main(String[] args) {
        readIn();
        //testPrint();
        constructEdgesToRemove();
        //testPrint();
        //removeRoutes();
        System.out.println(solve(residualGraph));

        //testPrint();
    }


    public static void removeRoutes() {
        int start = 0;
        int end = edgesToRemove.length;
        int mid;
        int ans = 0;

        while (start <= end) {
            List<Edge>[] updatedGraph = newGraph(new List[nodes]);
            for (int i = 0; i < residualGraph.length; i++) {
                updatedGraph[i] = residualGraph[i];
            }

            mid = (start + end) / 2;
            System.out.println("End " + end);
            updatedGraph = removeEdgesUntil(updatedGraph, mid);
            int newMaxFlow = solve(updatedGraph);
            System.out.println("New maxflow " + newMaxFlow);

            if (newMaxFlow >= students) {
                start = mid + 1;
            } else {
                ans = mid;
                end = mid - 1;
            }
        }
        int maxRemoved = ans;
        System.out.println(solve(removeEdgesUntil(residualGraph, maxRemoved)));

    }


    public static List<Edge>[] removeEdgesUntil(List<Edge>[] graph, int index) {
        Edge[] edgeRemove = new Edge[index];
        int counter = 0;

        for (int i = 0; i < index; i++) {
             edgeRemove[i] = edgesToRemoveCopy.poll();
        }

        for (int i = 0; i < graph.length; i ++) {
            System.out.println(graph[i]);
        }

        return graph;
    }

    public static void constructEdgesToRemove() {
        int indexToRemove;
        edgesToRemoveCopy = new LinkedList<>();
        for (int i = 0; i < routesToRemove.length; i++) {
            indexToRemove = routesToRemove[i];
            edgesToRemove[i] = graph[indexToRemove];
            edgesToRemoveCopy.offer(graph[indexToRemove]);
        }
    }


    public static int solve(List<Edge>[] graph) {
        int flow;
        do {
            resetVisitedNodes();
            flow = bfs(graph);
            maxFlow += flow;
        } while (flow != 0);
        return maxFlow;
    }

    public static int bfs(List<Edge>[] graph) {
        Queue<Integer> q = new ArrayDeque<>(nodes);
        visit(s);
        q.offer(s);

        Edge[] prev = new Edge[nodes];
        while (!q.isEmpty()) {
            int node = q.poll();
            if (node == t) break;

            for (Edge edge : graph[node]) {
                if (edge.remainingCapacity() > 0 && !visited(edge.to)) {
                    visit(edge.to);
                    prev[edge.to] = edge;
                    q.offer(edge.to);
                }
            }
        }

        if (prev[t] == null) return 0;

        // Find max capacity for the path
        int maxCapacity = Integer.MAX_VALUE;
        for (Edge edge = prev[t]; edge != null; edge = prev[edge.from])
            maxCapacity = min(maxCapacity, edge.remainingCapacity());

        //Update flow values.
        for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]) edge.augment(maxCapacity);

        return maxCapacity;
    }

    public static void visit(int i) {
        visited[i] = visitedIdentifier;
    }

    public static void resetVisitedNodes() {
        visitedIdentifier++;
    }

    public static boolean visited(int i) {
        return visited[i] == visitedIdentifier;
    }


    public static void readIn() {
        Scanner scanner = new Scanner(System.in);

        String[] temp = scanner.nextLine().split(" ");
        nodes = Integer.parseInt(temp[0]);
        edges = Integer.parseInt(temp[1]);
        students = Integer.parseInt(temp[2]);
        routes = Integer.parseInt(temp[3]);

        initialize();

        for (int i = 0; i < edges; i++) {
            String[] temp1 = scanner.nextLine().split(" ");
            int from = Integer.parseInt(temp1[0]);
            int to = Integer.parseInt(temp1[1]);
            int capacity = Integer.parseInt(temp1[2]);
            Edge edge = new Edge(from, to, capacity);
            graph[i] = edge;
            addEdges(edge);
        }
        for (int i = 0; i < routes; i++) routesToRemove[i] = scanner.nextInt();

        scanner.close();
    }

    private static void initialize() {
        residualGraph = new List[nodes]; //Adjacency List
        graph = new Edge[edges];
        edgesToRemove = new Edge[routes]; //Edges to be removed (in order)
        routesToRemove = new int[routes]; //Priority which edges to remove

        s = 0;
        t = nodes - 1;
        visited = new int[nodes];

        newGraph(residualGraph);

    }

    private static List<Edge>[] newGraph(List<Edge>[] graph) {
        for (int i = 0; i < nodes; i++) {
            graph[i] = new ArrayList<>();
        }
        return graph;
    }

    private static void addEdges(Edge edge) {
        Edge backEdge = new Edge(edge.to, edge.from, 0);
        edge.residual = backEdge;
        backEdge.residual = edge;
        residualGraph[edge.from].add(edge);
        residualGraph[edge.from].add(backEdge);
//        graphCopy[edge.from].add(edge);
//        graphCopy[edge.from].add(backEdge);
    }

    public static class Edge {
        int from, to;
        int flow = 0;
        int capacity;
        Edge residual;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }

        public void augment(int maxCapacity) {
            flow += maxCapacity;
            residual.flow -= maxCapacity;
        }

        public int remainingCapacity() {
            return capacity - flow;
        }

        public String toString() {
            return String.format("From: %d To: %d, Capacity: %d", from, to, capacity);
        }
    }

    private static void testPrint() {
//        System.out.printf("N: %d，M: %d，C: %d，P: %d \n", nodes, edges, students, routes);
//        System.out.printf("Source: %d Sink: %d \n", s, t);
        for (int i = 0; i < nodes; i++) {
            for (Edge e : residualGraph[i]) {
                System.out.println("Edge in Residual graph: " + e);
            }
        }

        for (int i = 0; i < nodes; i++) {
            for (Edge e : graphCopy[i]) {
                System.out.println("Edge in Graph Copy: " + e);
            }
        }

//        for (Edge e : graph) {
//            System.out.println("Edge in graph: " + e);
//        }
//
        for (Edge e : edgesToRemove) {
            System.out.println("Edges to remove: " + e);
        }
    }
}

