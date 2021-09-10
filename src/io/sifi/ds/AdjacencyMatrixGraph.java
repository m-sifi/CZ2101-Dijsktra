package io.sifi.ds;

import java.util.Arrays;

public class AdjacencyMatrixGraph extends AbstractGraph {

    public int matrix[][];

    public AdjacencyMatrixGraph(int V) {
        super(V);
        matrix = new int[V][V];
    }

    public void addEdge(int u, int v, int weight) {
        assert u > 0 && u <= V;
        assert v > 0 && v <= V;

        matrix[u-1][v-1] = weight;
        E++;
    }

    public int getWeight(int u, int v) {
        return matrix[u-1][v-1];
    }

    @Override
    public String toString() {
        return Arrays.deepToString(matrix).replace("], ", "]\n");
    }
}
