import java.util.*;

public class RailSolution {

    private static int nbrNodes, nbrStudents, nbrRoutes, s, t;
    private static int[][] graph;
    private static Edge[] edges, edgesOrdered;

    public static void main(String[] args) {
//        long t1 = System.currentTimeMillis();
        readIn();
//        long t = System.currentTimeMillis();
//        System.out.println(" Read in: " + (t - t1) + " ms");
        printResult();
//        long t2 = System.currentTimeMillis();
//        System.out.println("Algorithm: " + ( t2 - t) + " ms");
//        System.out.println("Total time: " + (t2 - t1) + " ms");
    }

    private static void readIn() {
        Scanner scan = new Scanner(System.in);
        nbrNodes = scan.nextInt();
        int nbrEdges = scan.nextInt();
        nbrStudents = scan.nextInt();
        nbrRoutes = scan.nextInt();
        scan.nextLine();

        graph = new int[nbrNodes][nbrNodes];
        edges = new Edge[nbrEdges];
        edgesOrdered = new Edge[nbrEdges];

        for (int i = 0; i < nbrEdges; i++) {
            String[] temp = scan.nextLine().split(" ");

            int from = Integer.parseInt(temp[0]);
            int to = Integer.parseInt(temp[1]);
            int capacity = Integer.parseInt(temp[2]);

            Edge newEdge = new Edge(from, to);
            graph[from][to] = capacity;
            graph[to][from] = capacity;

            edges[i] = newEdge;
        }

        for (int i = 0; i < nbrRoutes; i++) {
            int index = scan.nextInt();
            edgesOrdered[i] = edges[index];
        }

        s = 0;
        t = nbrNodes - 1;
    }

    //Checks how many roads can be removed with binary search and prints out how many roads were removed and the maxFlow.
    private static void printResult() {
        int start = 0;
        int end = nbrRoutes;
        int ans = 0;
        int newMaxFlow, mid;

        while (start <= end) {
            mid = (start + end) / 2;

            int[][] updatedNodeGraph = removeRoutesUntil(mid);
            newMaxFlow = getMaxFlow(updatedNodeGraph);

            // Try to remove more routes if maxFlow is >= nbrStudents
            if (newMaxFlow >= nbrStudents) {
                start = mid + 1;
            }
            // Otherwise try to remove less routes if maxFlow < nbrStudents
            else {
                ans = mid;
                end = mid - 1;
            }
        }
        //Check what the maxFlow is once the max amount of routes are removed
        int maxRemoved = ans - 1;
        int maxFlow = getMaxFlow(removeRoutesUntil(maxRemoved));
        System.out.println(maxRemoved + " " + maxFlow);
    }

    private static int[][] removeRoutesUntil(int index) {
        int[][] newGraph = copyGraph(graph);
        for (int i = 0; i < index; i++) {
            Edge removedEdge = edgesOrdered[i];
            newGraph[removedEdge.from][removedEdge.to] = 0;
            newGraph[removedEdge.to][removedEdge.from] = 0;
        }
        return newGraph;
    }

    private static int getMaxFlow(int[][] graph) {
        int[][] residualGraph = new int[nbrNodes][nbrNodes];

        for (int u = 0; u < nbrNodes; u++) {
            for (int v = 0; v < nbrNodes; v++) {
                residualGraph[u][v] = graph[u][v];
            }
        }

        int[] prev = new int[nbrNodes];

        int maxFlow = 0;

        while (pathExists(residualGraph, prev)) {
            int pathFlow = Integer.MAX_VALUE;

            //Bottleneck capacity for the route
            for (int t = RailSolution.t; t != s; t = prev[t]) {
                int edge = prev[t];
                pathFlow = Math.min(pathFlow, residualGraph[edge][t]);
            }

            //Augment edges
            for (int v = t; v != s; v = prev[v]) {
                int u = prev[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            maxFlow += pathFlow;
        }
        return maxFlow;
    }

    //Checks if there is a path from s to t.
    private static boolean pathExists(int[][] residualGraph, int[] previousEdges) {
        boolean[] visited = new boolean[nbrNodes];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        previousEdges[s] = -1;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            for (int neighbour = 0; neighbour < nbrNodes; neighbour++) {
                if (!visited[neighbour] && residualGraph[currentNode][neighbour] > 0) {
                    queue.add(neighbour);
                    previousEdges[neighbour] = currentNode;
                    visited[neighbour] = true;
                }
            }
        }

        return visited[t];
    }

    private static int[][] copyGraph(int[][] graph) {
        int[][] graphCopy = new int[nbrNodes][nbrNodes];
        for (int i = 0; i < nbrNodes; i++) {
            for (int j = 0; j < nbrNodes; j++) {
                graphCopy[i][j] = graph[i][j];
            }
        }
        return graphCopy;
    }

    private static class Edge {
        private int from, to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public String toString() {
            return "Edge " + from + " " + to;
        }
    }
}