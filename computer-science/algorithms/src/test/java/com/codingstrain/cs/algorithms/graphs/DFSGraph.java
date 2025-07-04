package com.codingstrain.cs.algorithms.graphs;

import java.util.ArrayList;
import java.util.List;

public class DFSGraph {
    private int verts;
    private List<List<Integer>> adjList;

    public DFSGraph(int vertices) {
        this.verts = vertices;
        adjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination) {
        adjList.get(source).add(destination);
        adjList.get(destination).add(source); // For undirected graph
    }

    public void dfs() {
        boolean[] visited = new boolean[verts];
        for (int i = 0; i < verts; i++) {
            if (!visited[i]) {
                dfsUtil(i, visited);
            }
        }
    }

    private void dfsUtil(int currentVertex, boolean[] visited) {
        visited[currentVertex] = true;
        System.out.print(currentVertex + " ");

        for (int neighbor : adjList.get(currentVertex)) {
            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited);
            }
        }
    }

    public static void main(String[] args) {
        DFSGraph graph = new DFSGraph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);

        System.out.println("DFS traversal of the graph:");
        graph.dfs();
    }
}