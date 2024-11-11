import java.util.*;

class Graph {
    private final int numNodes;
    private final int[][] adjMatrix;

    Graph(int numNodes){
        this.numNodes = numNodes;
        adjMatrix = new int[numNodes][numNodes];
        for(int i=0; i<numNodes; i++){
            Arrays.fill(adjMatrix[i], Integer.MAX_VALUE);
            adjMatrix[i][i] = 0;
        }
    }

    void addEdge(int src, int dest, int weight){
        adjMatrix[src][dest] = weight;
        adjMatrix[dest][src] = weight;
    }

    void bellmanFord(int start){
        int[] distances = new int[numNodes];
        int[] predecessors = new int[numNodes];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(predecessors, -1);
        distances[start] = 0;
        for(int i=1; i<numNodes; i++){
            System.out.println("\nIteration " + i + ":");
            boolean anyUpdate = false;
            for(int u=0; u<numNodes; u++){
                for(int v=0; v<numNodes; v++){
                    if(adjMatrix[u][v]!=Integer.MAX_VALUE && distances[u]!=Integer.MAX_VALUE){
                        int newDist = distances[u] + adjMatrix[u][v];
                        if (newDist < distances[v]) {
                            distances[v] = newDist;
                            predecessors[v] = u;
                            anyUpdate = true;
                        }
                    }
                }
            }
            printDistances(distances);
            if(!anyUpdate){
                System.out.println("No updates..stopping");
                break;
            }
        }
        for(int u=0; u<numNodes; u++){
            for(int v=0; v<numNodes; v++){
                if(adjMatrix[u][v]!=Integer.MAX_VALUE && distances[u]!=Integer.MAX_VALUE){
                    if(distances[u]+adjMatrix[u][v] < distances[v]){
                        System.out.println("Graph contains a negative-weight cycle");
                        return;
                    }
                }
            }
        }
        System.out.println("\nFinal Routing Table for Node " + start + ":");
        printRoutingTable(start, distances, predecessors);
    }

    private void printDistances(int[] distances){
        System.out.println("Node   Distance");
        for(int i=0; i<distances.length; i++){
            System.out.printf("%-7d %-18d\n", i+1, (distances[i]==Integer.MAX_VALUE? -1 : distances[i]));
        }
    }

    private void printRoutingTable(int start, int[] distances, int[] predecessors) {
        System.out.printf("%-15s %-15s %-15s\n", "Destination", "Distance", "Next Hop");
        for(int i=0; i<numNodes; i++){
            if(i!=start){
                int nextHop = findNextHop(start, i, predecessors);
                System.out.printf("%-15d %-15d %-15d\n", i+1, (distances[i]==Integer.MAX_VALUE? -1:distances[i]), nextHop+1);
            }
        }
    }

    private int findNextHop(int start, int dest, int[] predecessors){
        int current = dest;
        while(predecessors[current]!=start && predecessors[current]!=-1){
            current = predecessors[current];
        }
        return (predecessors[current]==-1) ? -1 : current;
    }
}

public class bellmanford {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of nodes: ");
        int numNodes = sc.nextInt();
        Graph graph = new Graph(numNodes);
        System.out.print("Enter the number of edges: ");
        int numEdges = sc.nextInt();
        System.out.println("Enter each edge with src, dest, and weight:");
        for(int i=0; i<numEdges; i++){
            int src = sc.nextInt()-1;
            int dest = sc.nextInt()-1;
            int weight = sc.nextInt();
            graph.addEdge(src, dest, weight);
        }
        System.out.print("Enter the source node: ");
        int startNode = sc.nextInt()-1;
        sc.close();
        graph.bellmanFord(startNode);
    }
}