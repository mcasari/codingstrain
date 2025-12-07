package com.codingstrain.tweetexamples;

public class Example {

    class TreeNode {
        int value;
        TreeNode left, right;

        TreeNode(int value) {
            this.value = value;
            left = right = null;
        }
    }

    private TreeNode root;

    public Example() {
        root = null;
    }

    // Add a new node to the tree
    public void add(int value) {
        root = addRecursively(root, value);
    }

    // Helper method for recursive node addition
    private TreeNode addRecursively(TreeNode node, int nodeVal) {
        if (node == null) {
            return new TreeNode(nodeVal);
        }
        if (nodeVal < node.value) {
            node.left = addRecursively(node.left, nodeVal);
        } else if (nodeVal > node.value) {
            node.right = addRecursively(node.right, nodeVal);
        }
        return node;
    }

    // Check if a value exists in the tree
    public boolean contains(int value) {
        return containsRecursively(root, value);
    }

    // Helper method for recursive search
    private boolean containsRecursively(TreeNode node, int nodeVAl) {
        if (node == null) {
            return false;
        }
        if (nodeVAl == node.value) {
            return true;
        }
        if (nodeVAl < node.value) {
            return containsRecursively(node.left, nodeVAl);
        }
        return containsRecursively(node.right, nodeVAl);
    }

    // Perform an in-order traversal and print the values
    public void traverseInOrder() {
        inOrderRecursively(root);
    }

    private void inOrderRecursively(TreeNode node) {
        if (node != null) {
            inOrderRecursively(node.left);
            System.out.print(node.value + " ");
            inOrderRecursively(node.right);
        }
    }

    public static void main(String[] args) {
        Example binaryTree = new Example();
        binaryTree.add(10);
        binaryTree.add(5);
        binaryTree.add(15);
        binaryTree.add(3);
        binaryTree.add(7);

        //
        System.out.println("In-order traversal:");
        binaryTree.traverseInOrder(); // Expected output: 3 5 7 10 15
        System.out.println("\nDoes the tree contain 7? " + binaryTree.contains(7)); // Expected output: true
        //
    }
}