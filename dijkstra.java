import java.util.*;

class Graph {
    private final int numNodes;
    private final int[][] adjacencyMatrix;

    Graph(int numNodes){
        this.numNodes = numNodes;
        adjacencyMatrix = new int[numNodes][numNodes];
        for(int i=0; i <numNodes; i++){
            Arrays.fill(adjacencyMatrix[i], Integer.MAX_VALUE);
            adjacencyMatrix[i][i] = 0;
        }
    }

    void addEdge(int src, int dest, int weight){
        adjacencyMatrix[src][dest] = weight;
        adjacencyMatrix[dest][src] = weight;
    }

    Map<Integer, Integer> dijkstra(int src) {
        int[] distances = new int[numNodes];
        boolean[] visited = new boolean[numNodes];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[src] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{src, 0});
        Map<Integer, Integer> routingTable = new HashMap<>();
        while(!pq.isEmpty()){
            int[] nodeInfo = pq.poll();
            int u = nodeInfo[0];
            if (visited[u]) continue;
            visited[u] = true;
            for(int v=0; v<numNodes; v++){
                if(adjacencyMatrix[u][v]!=Integer.MAX_VALUE && !visited[v]){
                    int newDist = distances[u] + adjacencyMatrix[u][v];
                    if(newDist<distances[v]){
                        distances[v] = newDist;
                        pq.add(new int[]{v, distances[v]});
                        routingTable.put(v, u);
                    }
                }
            }
        }
        return routingTable;
    }

    void displayRoutingTables(){
        for(int i=0; i<numNodes; i++){
            Map<Integer, Integer> routingTable = dijkstra(i);
            System.out.println("Routing Table for Node " + i + ":");
            System.out.printf("%-15s %-15s\n", "Destination", "Next Hop");
            for(int j=0; j<numNodes; j++){
                if(j!=i){
                    Integer nextHop = routingTable.getOrDefault(j, -1);
                    if(nextHop!=-1){
                        System.out.printf("%-15d %-15d\n", j, nextHop);
                    }
                    else{
                        System.out.printf("%-15d %-15s\n", j, "Unreachable");
                    }
                }
            }
            System.out.println();
        }
    }
}

public class dijkstra{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of nodes: ");
        int numNodes = sc.nextInt();
        Graph graph = new Graph(numNodes);
        System.out.print("Enter the number of edges: ");
        int numEdges = sc.nextInt();
        System.out.println("Enter each edge with src, dest, and weight:");
        for(int i=0; i<numEdges; i++){
            int src = sc.nextInt();
            int dest = sc.nextInt();
            int weight = sc.nextInt();
            graph.addEdge(src, dest, weight);
        }
        sc.close();
        System.out.println("\nRouting Tables");
        graph.displayRoutingTables();
    }
}