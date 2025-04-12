package com.codingstrain.cs.datastructures;


public class BinarySearchTree {
	
    class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    Node root;

    public BinarySearchTree() {
        root = null;
    }

    // Insert a node into the BST
    public void insert(int data) {
        root = insertRec(root, data);
    }

    // Recursive function to insert a new node
    private Node insertRec(Node root, int data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    // Search for a node with the given value
    public boolean search(int data) {
        return searchRec(root, data);
    }

    // Recursive function to search a node
    private boolean searchRec(Node root, int data) {
        if (root == null) {
            return false;
        }
        if (data == root.data) {
            return true;
        }
        if (data < root.data) {
            return searchRec(root.left, data);
        }
        return searchRec(root.right, data);
    }

    // In-order traversal of the tree
    public void inorder() {
        inorderRec(root);
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }
    
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);
        tree.insert(7);

        System.out.println("In-order traversal:");
        tree.inorder(); // Output: 3 5 7 10 15

        System.out.println("\nSearching for 7 in the tree: " + tree.search(7)); // Output: true
    }
}

