package io.sifi;

import io.sifi.ds.AdjacencyMatrixGraph;
import io.sifi.ds.Graph;
import io.sifi.ds.Node;

import java.util.Scanner;

public class DijkstraArrayExample {

    public static final int INFINITY = Integer.MAX_VALUE;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter |V|: ");
        int V = sc.nextInt();

        System.out.printf("Please enter |E|: ");
        int E = sc.nextInt();

        if(E < V)
            throw new RuntimeException("|E| has to be >= |V|");

        AdjacencyMatrixGraph g = new AdjacencyMatrixGraph(20);
        Graph.Random(g, E);
        Dijikstra(g, 1);
    }

    public static void Dijikstra(AdjacencyMatrixGraph g, int source) {
        int V = g.getV();
        Node[] nodes = new Node[V];

        for(int i = 0; i < V; i++)
            nodes[i] = new Node(i + 1, INFINITY);
        nodes[source-1].distance = 0;

        for(int i = 0; i < V - 1; i++) {
            int u = MinDistance(g, nodes);
            nodes[u].visited = true;

            for(int j = 0; j < V; j++) {
                if(!nodes[j].visited && g.matrix[u][j] != 0 && nodes[u].distance != INFINITY && nodes[u].distance + g.matrix[u][j] < nodes[j].distance)
                    nodes[j].distance = nodes[u].distance + g.matrix[u][j];
            }
        }

        Print(g, nodes);
    }

    public static int MinDistance(AdjacencyMatrixGraph g, Node[] nodes) {
        int min = INFINITY;
        int index = -1;
        for(int i = 0; i < g.getV(); i++) {
            if(!nodes[i].visited && nodes[i].distance <= min) {
                min = nodes[i].distance;
                index = i;
            }
        }

        return index;
    }

    public static void Print(AdjacencyMatrixGraph g, Node nodes[]) {
        System.out.println("Vertex \t\t Distance from Source");
        for (int i = 0; i < g.getV(); i++)
            System.out.println(i+1 + " \t\t " + nodes[i].distance);
    }
}
