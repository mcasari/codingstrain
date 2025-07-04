package com.codingstrain.cs.algorithms.graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSGraphAlg {
    private int verts;
    private List<List<Integer>> adjList;

    public BFSGraphAlg(int verts) {
        this.verts = verts;
        adjList = new ArrayList<>();
        for (int i = 0; i < verts; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addGraphEdge(int source, int destination) {
        adjList.get(source).add(destination);
        adjList.get(destination).add(source); // For undirected graph
    }

    public void bfSearh(int startVertex) {
        boolean[] alreadyVisited = new boolean[verts];
        Queue<Integer> q = new LinkedList<>();

        alreadyVisited[startVertex] = true;
        q.offer(startVertex);

        while (!q.isEmpty()) {
            int currentVertex = q.poll();
            System.out.print(currentVertex + " ");

            for (int neighbor : adjList.get(currentVertex)) {
                if (!alreadyVisited[neighbor]) {
                    alreadyVisited[neighbor] = true;
                    q.offer(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) {
        BFSGraphAlg graph = new BFSGraphAlg(5);
        graph.addGraphEdge(0, 1);
        graph.addGraphEdge(0, 2);
        graph.addGraphEdge(1, 3);
        graph.addGraphEdge(1, 4);

        System.out.println("BFS traversal of the graph starting from vertex 0:");
        graph.bfSearh(0);
    }
}