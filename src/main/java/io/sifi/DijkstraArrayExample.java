package io.sifi;

import io.sifi.ds.AdjacencyMatrixGraph;
import io.sifi.ds.Graph;
import io.sifi.ds.Node;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class DijkstraArrayExample {

    private static final String E_CSV = "./E_output.csv";
    private static final String V_CSV = "./V_output.csv";

    public static final int INFINITY = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {

        try(CSVPrinter printer = new CSVPrinter(new FileWriter(E_CSV), CSVFormat.EXCEL)) {

            printer.printRecord("V", "E", "Time");

            int V = 500;
            int MIN = V;
            int MAX = ((V * (V - 1)) / 2);

            for (int E = V; E < MAX; E+=V) {
                System.out.printf("In Progress: %.2f%%\n", (E / (float)MAX) * 100);
                AdjacencyMatrixGraph g = new AdjacencyMatrixGraph(V);
                Graph.Random(g, E);
                long startTime = System.nanoTime();
                Dijikstra(g, 1);
                long elapsed = System.nanoTime() - startTime;

                printer.printRecord(V, E, elapsed);
                printer.flush();
            }
        }

        try(CSVPrinter printer = new CSVPrinter(new FileWriter(V_CSV), CSVFormat.EXCEL)) {

            printer.printRecord("V", "E", "Time");

            int E = 1000;

            for (int V = 20; V < 10000; V+=100) {
                System.out.printf("In Progress: %.2f%%\n", (V / (float)10000) * 100);
                AdjacencyMatrixGraph g = new AdjacencyMatrixGraph(V);
                Graph.Random(g, 0.2f);
                long startTime = System.nanoTime();
                Dijikstra(g, 1);
                long elapsed = System.nanoTime() - startTime;

                printer.printRecord(V, g.getE(), elapsed);
                printer.flush();
            }
        }
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

//        Print(g, nodes);
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
