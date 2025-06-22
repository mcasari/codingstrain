package com.codingstrain.cs.algorithms.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class DijkstraAlg {
	
    static class GraphEdge {
        int target, weight;
        GraphEdge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    static class GraphNode implements Comparable<GraphNode> {
        int vertex, distance;
        GraphNode(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public int compareTo(GraphNode other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    public static int[] dijkstra(List<List<GraphEdge>> graph, int source) {
        int graphSize = graph.size();
        int[] dist = new int[graphSize];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        PriorityQueue<GraphNode> priorQ = new PriorityQueue<>();
        priorQ.add(new GraphNode(source, 0));

        while (!priorQ.isEmpty()) {
            GraphNode current = priorQ.poll();
            int vert = current.vertex;

            for (GraphEdge edge : graph.get(vert)) {
                int v = edge.target;
                int weight = edge.weight;

                if (dist[vert] + weight < dist[v]) {
                    dist[v] = dist[vert] + weight;
                    priorQ.add(new GraphNode(v, dist[v]));
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        int numVertices = 5;
        List<List<GraphEdge>> g = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            g.add(new ArrayList<>());
        }

        g.get(0).add(new GraphEdge(1, 10));
        g.get(0).add(new GraphEdge(4, 5));
        g.get(1).add(new GraphEdge(2, 1));
        g.get(1).add(new GraphEdge(4, 2));
        g.get(2).add(new GraphEdge(3, 4));
        g.get(3).add(new GraphEdge(2, 6));
        g.get(3).add(new GraphEdge(0, 7));
        g.get(4).add(new GraphEdge(1, 3));
        g.get(4).add(new GraphEdge(2, 9));
        g.get(4).add(new GraphEdge(3, 2));

        int[] distances = dijkstra(g, 0);

        System.out.println("Shortest distances from node 0:");
        for (int i = 0; i < distances.length; i++) {
            System.out.println("To node " + i + " : " + distances[i]);
        }
    }
}