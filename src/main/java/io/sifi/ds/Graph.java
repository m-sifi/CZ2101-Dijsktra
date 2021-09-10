package io.sifi.ds;

import java.util.Random;

public final class Graph {

    public static final int MIN_WEIGHT = 5;
    public static final int MAX_WEIGHT = 20;

    public static void Random(AbstractGraph g, float P) {
        int V = g.getV();

        ConnectEdges(g);
        GenerateRandomEdges(g, P);
    }

    public static void Random(AbstractGraph g, int E) {
        int V = g.getV();

        if(E < V)
            throw new RuntimeException("|E| must be at least >= |V|");

        ConnectEdges(g);
        GenerateRandomEdges(g, E);
    }

    private static void GenerateRandomEdges(AbstractGraph g, int E) {
        Random random = new Random();
        int V = g.getV();
        int u, v = -1;

        do {
            u = RandomV(random, V);
            v = RandomV(random, V);
            int weight = RandomWeight(random);

            g.addEdge(u, v, weight);
        } while(u == v || g.getWeight(u, v) != 0 && g.getE() < E);
    }

    private static void GenerateRandomEdges(AbstractGraph g, float P) {
        Random random = new Random();
        int V = g.getV();
        for(int u = 0; u < V; u++) {
            for(int v = 0; v < V; v++) {
                float r = random.nextFloat();

                if(r < P && u != v && g.getWeight(u+1, v+1) != 0) {
                    g.addEdge(u+1, v+1, RandomWeight(random));
                }
            }
        }

//        do {
//            u = RandomV(random, V);
//            v = RandomV(random, V);
//            int weight = RandomWeight(random);
//
//            g.addEdge(u, v, weight);
//        } while(u == v || g.getWeight(u, v) != 0);
    }

    private static void ConnectEdges(AbstractGraph g) {
        Random random = new Random();
        int V = g.getV();

        for(int u = 0; u < V; u++) {
            int v = (u+1) % V;
            int weight = RandomWeight(random);

            g.addEdge(u+1, v+1, weight);
        }

        g.addEdge(V, 1, RandomWeight(random));
    }

    private static int RandomV(Random rand, int V) {
        return rand.nextInt(V) + 1; // Generates from 1 - V;
    }

    private static int RandomWeight(Random rand) {
        return MIN_WEIGHT + rand.nextInt(MAX_WEIGHT - MIN_WEIGHT); // Generates from MIN_WEIGHT - MAX_WEIGHT;
    }
}
