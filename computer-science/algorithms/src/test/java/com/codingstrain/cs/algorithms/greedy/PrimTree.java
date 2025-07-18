package com.codingstrain.cs.algorithms.greedy;

import java.util.Arrays;

public class PrimTree {

    private static final int MAX = Integer.MAX_VALUE;

    public void constructTree(int[][] costMatrix) {
        int nArr = costMatrix.length;
        boolean[] inTree = new boolean[nArr];
        int[] minEdg = new int[nArr];
        int[] connectingVertex = new int[nArr];

        Arrays.fill(minEdg, MAX);
        minEdg[0] = 0;
        connectingVertex[0] = -1;

        for (int i = 0; i < nArr - 1; i++) {
            int u = chooseVertex(minEdg, inTree);
            inTree[u] = true;

            for (int v = 0; v < nArr; v++) {
                if (costMatrix[u][v] != 0 && !inTree[v] && costMatrix[u][v] < minEdg[v]) {
                    minEdg[v] = costMatrix[u][v];
                    connectingVertex[v] = u;
                }
            }
        }

        displayTree(connectingVertex, costMatrix);
    }

    private int chooseVertex(int[] minEdge, boolean[] inTr) {
        int min = MAX;
        int index = -1;

        for (int i = 0; i < minEdge.length; i++) {
            if (!inTr[i] && minEdge[i] < min) {
                min = minEdge[i];
                index = i;
            }
        }

        return index;
    }

    private void displayTree(int[] parent, int[][] graph) {
        for (int i = 1; i < graph.length; i++) {
            System.out.println(parent[i] + " -> " + i + "  cost: " + graph[i][parent[i]]);
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
            {0, 3, 0, 0, 6, 0},
            {3, 0, 1, 0, 2, 0},
            {0, 1, 0, 4, 0, 5},
            {0, 0, 4, 0, 0, 7},
            {6, 2, 0, 0, 0, 8},
            {0, 0, 5, 7, 8, 0}
        };

        PrimTree tree = new PrimTree();
        tree.constructTree(graph);
    }
}