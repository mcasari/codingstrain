package com.codingstrain.cs.algorithms.trees;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeWidth {
	
    static class BinTreeNode {
        int val;
        BinTreeNode left, right;
        BinTreeNode(int x) { val = x; }
    }

    static class NodeIndexPair {
        BinTreeNode node;
        int index;
        NodeIndexPair(BinTreeNode node, int index) {
            this.node = node;
            this.index = index;
        }
    }

    public int widthOfBinaryTree(BinTreeNode root) {
        if (root == null) return 0;

        int maxWidth = 0;
        Queue<NodeIndexPair> q = new LinkedList<>();
        q.offer(new NodeIndexPair(root, 0));

        while (!q.isEmpty()) {
            int size = q.size();
            int levelStart = q.peek().index;
            int levelEnd = levelStart;

            for (int i = 0; i < size; i++) {
                NodeIndexPair current = q.poll();
                BinTreeNode node = current.node;
                int index = current.index;

                levelEnd = index;

                if (node.left != null) {
                    q.offer(new NodeIndexPair(node.left, 2 * index));
                }
                if (node.right != null) {
                    q.offer(new NodeIndexPair(node.right, 2 * index + 1));
                }
            }
            maxWidth = Math.max(maxWidth, levelEnd - levelStart + 1);
        }

        return maxWidth;
    }

    // Example usage
    public static void main(String[] args) {
        BinaryTreeWidth solver = new BinaryTreeWidth();
        BinTreeNode root = new BinTreeNode(1);
        root.left = new BinTreeNode(3);
        root.right = new BinTreeNode(2);
        root.left.left = new BinTreeNode(5);
        root.left.right = new BinTreeNode(3);
        root.right.right = new BinTreeNode(9);

        int result = solver.widthOfBinaryTree(root);
        System.out.println("Maximum width: " + result); // Output should be 4
    }
}