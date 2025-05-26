package com.codingstrain.cs.algorithms.trees;

class BinaryTreeToDLL {
	
    static class BinTreeNode {
        int data;
        BinTreeNode left, right;

        BinTreeNode(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }

    static class Converter {
        BinTreeNode prev = null;
        BinTreeNode head = null;

        public void convertToDLL(BinTreeNode root) {
            if (root == null) return;

            convertToDLL(root.left);

            if (prev == null) {
                head = root;
            } else {
                root.left = prev;
                prev.right = root;
            }
            prev = root;

            convertToDLL(root.right);
        }

        public BinTreeNode getHead() {
            return head;
        }
    }

    public static void printDLL(BinTreeNode head) {
        BinTreeNode current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.right;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        BinTreeNode root = new BinTreeNode(10);
        root.left = new BinTreeNode(6);
        root.right = new BinTreeNode(14);
        root.left.left = new BinTreeNode(4);
        root.left.right = new BinTreeNode(8);
        root.right.left = new BinTreeNode(12);
        root.right.right = new BinTreeNode(16);

        Converter converter = new Converter();
        converter.convertToDLL(root);
        BinTreeNode dllHead = converter.getHead();

        System.out.println("Doubly Linked List:");
        printDLL(dllHead);
    }
}