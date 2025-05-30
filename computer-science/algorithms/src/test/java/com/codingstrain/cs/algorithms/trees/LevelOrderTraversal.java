package com.codingstrain.cs.algorithms.trees;

import java.util.LinkedList;
import java.util.Queue;

class BinaryNode {
    int key;
    BinaryNode leftBranch;
    BinaryNode rightBranch;

    BinaryNode(int key) {
        this.key = key;
    }
}

public class LevelOrderTraversal {

    public static void printByLayer(BinaryNode startNode) {
        if (startNode == null) return;

        Queue<BinaryNode> pendingNodes = new LinkedList<>();
        pendingNodes.add(startNode);

        while (!pendingNodes.isEmpty()) {
            BinaryNode active = pendingNodes.poll();
            System.out.print(active.key + " ");

            if (active.leftBranch != null) {
                pendingNodes.add(active.leftBranch);
            }

            if (active.rightBranch != null) {
                pendingNodes.add(active.rightBranch);
            }
        }
    }

    public static void main(String[] args) {
        /*
                8
               / \
              4   12
             / \    \
            2   6    14
        */

        BinaryNode treeRoot = new BinaryNode(8);
        treeRoot.leftBranch = new BinaryNode(4);
        treeRoot.rightBranch = new BinaryNode(12);
        treeRoot.leftBranch.leftBranch = new BinaryNode(2);
        treeRoot.leftBranch.rightBranch = new BinaryNode(6);
        treeRoot.rightBranch.rightBranch = new BinaryNode(14);

        System.out.print("Level Order Output: ");
        printByLayer(treeRoot);
    }
}