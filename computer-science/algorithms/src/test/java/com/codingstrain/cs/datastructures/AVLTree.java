package com.codingstrain.cs.datastructures;


public class AVLTree {

    private class TreeNode {
        int value;
        int nodeHeight;
        TreeNode leftChild;
        TreeNode rightChild;

        TreeNode(int val) {
            this.value = val;
            this.nodeHeight = 1;
        }
    }

    private TreeNode rootNode;

    private int computeHeight(TreeNode node) {
        if (node == null) return 0;
        return node.nodeHeight;
    }

    private int calculateBalance(TreeNode node) {
        if (node == null) return 0;
        return computeHeight(node.leftChild) - computeHeight(node.rightChild);
    }

    private TreeNode rotateR(TreeNode y) {
        TreeNode x = y.leftChild;
        TreeNode temp = x.rightChild;

        x.rightChild = y;
        y.leftChild = temp;

        y.nodeHeight = 1 + Math.max(computeHeight(y.leftChild), computeHeight(y.rightChild));
        x.nodeHeight = 1 + Math.max(computeHeight(x.leftChild), computeHeight(x.rightChild));

        return x;
    }

    private TreeNode rotateL(TreeNode x) {
        TreeNode y = x.rightChild;
        TreeNode temp = y.leftChild;

        y.leftChild = x;
        x.rightChild = temp;

        x.nodeHeight = 1 + Math.max(computeHeight(x.leftChild), computeHeight(x.rightChild));
        y.nodeHeight = 1 + Math.max(computeHeight(y.leftChild), computeHeight(y.rightChild));

        return y;
    }

    private TreeNode addNode(TreeNode node, int newValue) {
        if (node == null) {
            return new TreeNode(newValue);
        }

        if (newValue < node.value) {
            node.leftChild = addNode(node.leftChild, newValue);
        } else if (newValue > node.value) {
            node.rightChild = addNode(node.rightChild, newValue);
        } else {
            // Duplicate values not allowed
            return node;
        }

        node.nodeHeight = 1 + Math.max(computeHeight(node.leftChild), computeHeight(node.rightChild));
        int balance = calculateBalance(node);

        if (balance > 1 && newValue < node.leftChild.value)
            return rotateR(node);

        if (balance < -1 && newValue > node.rightChild.value)
            return rotateL(node);

        if (balance > 1 && newValue > node.leftChild.value) {
            node.leftChild = rotateL(node.leftChild);
            return rotateR(node);
        }

        if (balance < -1 && newValue < node.rightChild.value) {
            node.rightChild = rotateR(node.rightChild);
            return rotateL(node);
        }

        return node;
    }

    public void insert(int value) {
        rootNode = addNode(rootNode, value);
    }

    public void displayInOrder() {
        traverseInOrder(rootNode);
        System.out.println();
    }

    private void traverseInOrder(TreeNode current) {
        if (current != null) {
            traverseInOrder(current.leftChild);
            System.out.print(current.value + " ");
            traverseInOrder(current.rightChild);
        }
    }

    public static void main(String[] args) {
        AVLTree avl = new AVLTree();
        avl.insert(25);
        avl.insert(15);
        avl.insert(30);
        avl.insert(10);

        System.out.print("AVL In-order: ");
        avl.displayInOrder(); // Expected Output: 10 15 25 30
    }
}

