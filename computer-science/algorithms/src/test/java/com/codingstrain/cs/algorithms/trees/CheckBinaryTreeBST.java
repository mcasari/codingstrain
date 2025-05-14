package com.codingstrain.cs.algorithms.trees;

public class CheckBinaryTreeBST {

	class TreeNode {
	    int val;
	    TreeNode left, right;
	    TreeNode(int val) {
	        this.val = val;
	    }
	}

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return isValidBST(node.left, min, node.val) &&
               isValidBST(node.right, node.val, max);
    }

    public static void main(String[] args) {
        /*
              10
             /  \
            5    15
                /  \
               12   20
        */
        TreeNode root = new CheckBinaryTreeBST().new TreeNode(10);
        root.left = new CheckBinaryTreeBST().new TreeNode(5);
        root.right = new CheckBinaryTreeBST().new TreeNode(15);
        root.right.left = new CheckBinaryTreeBST().new TreeNode(12);
        root.right.right = new CheckBinaryTreeBST().new TreeNode(20);

        CheckBinaryTreeBST validator = new CheckBinaryTreeBST();
        boolean result = validator.isValidBST(root);
        System.out.println("Is the tree a valid BST? " + result);
    }
}

