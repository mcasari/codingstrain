package com.codingstrain.cs.algorithms.trees;


public class IdenticalBinaryTrees {

	class BinTreeNode {
	    int val;
	    BinTreeNode left;
	    BinTreeNode right;

	    BinTreeNode(int x) {
	        val = x;
	    }
	}
	
    public static boolean areIdentical(BinTreeNode first, BinTreeNode second) {
        if (first == null && second == null) {
            return true;
        }

        if (first == null || second == null) {
            return false;
        }

        if (first.val != second.val) {
            return false;
        }

        return areIdentical(first.left, second.left) && areIdentical(first.right, second.right);
    }

    public static void main(String[] args) {
        BinTreeNode binTreeNode = new IdenticalBinaryTrees().new BinTreeNode(1);
        binTreeNode.left = new IdenticalBinaryTrees().new BinTreeNode(2);
        binTreeNode.right = new IdenticalBinaryTrees().new BinTreeNode(3);

        BinTreeNode tree2 = new IdenticalBinaryTrees().new BinTreeNode(1);
        tree2.left = new IdenticalBinaryTrees().new BinTreeNode(2);
        tree2.right = new IdenticalBinaryTrees().new BinTreeNode(3);

        boolean result = areIdentical(binTreeNode, tree2);
        System.out.println("Are the two binary trees identical? " + result);
    }
}