import java.util.*;

class Graph {
    private final int numNodes;
    private final int[][] adjMatrix;

    Graph(int numNodes) {
        this.numNodes = numNodes;
        adjMatrix = new int[numNodes][numNodes];
        for (int i = 0; i < numNodes; i++) {
            Arrays.fill(adjMatrix[i], Integer.MAX_VALUE); // No edge by default
        }
    }

    // Adds an undirected edge with the given weight
    void addEdge(int src, int dest, int weight) {
        adjMatrix[src][dest] = weight;
        adjMatrix[dest][src] = weight; // Because the graph is undirected
    }

    void dijkstra(int start) {
        int[] distances = new int[numNodes];
        int[] predecessors = new int[numNodes];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(predecessors, -1);
        distances[start] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{start, 0});
        int iteration = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int distance = current[1];
            if (distance > distances[node]) continue;

            System.out.println("\nIteration " + (++iteration));
            System.out.println("Discovering Node " + node);
            System.out.println("Current Shortest Distance: " + distances[node]);

            // Explore neighbors (check all other nodes)
            for (int neighbor = 0; neighbor < numNodes; neighbor++) {
                if (adjMatrix[node][neighbor] != Integer.MAX_VALUE) {
                    int weight = adjMatrix[node][neighbor];
                    int newDist = distances[node] + weight;
                    if (newDist < distances[neighbor]) {
                        distances[neighbor] = newDist;
                        predecessors[neighbor] = node;
                        pq.offer(new int[]{neighbor, newDist});
                    }
                }
            }
            printDistances(distances);
        }

        System.out.println("\nFinal Routing Table for Node " + start + ":");
        printRoutingTable(start, distances, predecessors);
    }

    private void printDistances(int[] distances) {
        System.out.println("Node   Distance");
        for (int i = 0; i < distances.length; i++) {
            System.out.printf("%-7d %-18d\n", i, (distances[i] == Integer.MAX_VALUE ? -1 : distances[i]));
        }
    }

    private void printRoutingTable(int start, int[] distances, int[] predecessors) {
        System.out.printf("%-15s %-15s %-15s\n", "Destination", "Distance", "Next Hop");
        for (int i = 0; i < numNodes; i++) {
            if (i != start) {
                int nextHop = findNextHop(start, i, predecessors);
                System.out.printf("%-15d %-15d %-15d\n", i, 
                (distances[i] == Integer.MAX_VALUE ? "inf" : distances[i]), nextHop);
            }
        }
    }

    private int findNextHop(int start, int dest, int[] predecessors) {
        int current = dest;
        while (predecessors[current] != start && predecessors[current] != -1) {
            current = predecessors[current];
        }
        return (predecessors[current] == -1) ? -1 : current;
    }
}

public class dijkstra {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of nodes: ");
        int numNodes = sc.nextInt();
        Graph graph = new Graph(numNodes);
        System.out.print("Enter the number of edges: ");
        int numEdges = sc.nextInt();
        System.out.println("Enter each edge with src, dest, and weight:");
        for (int i = 0; i < numEdges; i++) {
            int src = sc.nextInt();
            int dest = sc.nextInt();
            int weight = sc.nextInt();
            graph.addEdge(src, dest, weight);
        }
        System.out.print("Enter the source node: ");
        int startNode = sc.nextInt();
        sc.close();
        graph.dijkstra(startNode);
    }
}