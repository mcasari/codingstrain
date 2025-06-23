package com.codingstrain.cs.algorithms.graphs;
import java.util.*;

public class KruskalAlg {
    public static void main(String[] args) {
        int graphVertices = 6;
        List<GraphEdge> graphEdges = new ArrayList<>();

        graphEdges.add(new KruskalAlg().new GraphEdge(0, 1, 4));
        graphEdges.add(new KruskalAlg().new GraphEdge(0, 2, 4));
        graphEdges.add(new KruskalAlg().new GraphEdge(1, 2, 2));
        graphEdges.add(new KruskalAlg().new GraphEdge(1, 3, 5));
        graphEdges.add(new KruskalAlg().new GraphEdge(2, 3, 5));
        graphEdges.add(new KruskalAlg().new GraphEdge(2, 4, 11));
        graphEdges.add(new KruskalAlg().new GraphEdge(3, 4, 2));
        graphEdges.add(new KruskalAlg().new GraphEdge(3, 5, 1));
        graphEdges.add(new KruskalAlg().new GraphEdge(4, 5, 7));

        List<GraphEdge> mst = kruskalMST(graphVertices, graphEdges);

        System.out.println("Edges in the Minimum Spanning Tree:");
        for (GraphEdge e : mst) {
            System.out.println(e.src + " - " + e.dest + " : " + e.weight);
        }
    }

    static List<GraphEdge> kruskalMST(int vertices, List<GraphEdge> edges) {
        Collections.sort(edges);
        UnionFindOp uf = new KruskalAlg().new UnionFindOp(vertices);
        List<GraphEdge> mst = new ArrayList<>();

        for (GraphEdge edge : edges) {
            if (uf.union(edge.src, edge.dest)) {
                mst.add(edge);
                if (mst.size() == vertices - 1)
                    break;
            }
        }

        return mst;
    }
    
    class GraphEdge implements Comparable<GraphEdge> {
        int src, dest, weight;

        GraphEdge(int s, int d, int w) {
            this.src = s;
            this.dest = d;
            this.weight = w;
        }

        public int compareTo(GraphEdge other) {
            return this.weight - other.weight;
        }
    }

    class UnionFindOp {
        int[] parent, rank;

        UnionFindOp(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++)
                parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int x, int y) {
            int xr = find(x);
            int yr = find(y);
            if (xr == yr) return false;

            if (rank[xr] < rank[yr]) {
                parent[xr] = yr;
            } else if (rank[xr] > rank[yr]) {
                parent[yr] = xr;
            } else {
                parent[yr] = xr;
                rank[xr]++;
            }
            return true;
        }
    }
}
