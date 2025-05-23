package com.codingstrain.cs.algorithms.trees;
import java.util.Stack;


public class InorderTraversal {
	
	class TreeNode {
	    int value;
	    TreeNode left;
	    TreeNode right;

	    TreeNode(int value) {
	        this.value = value;
	        this.left = null;
	        this.right = null;
	    }
	}

    public static void inorderIterative(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            // Reach the leftmost node of the current node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Current must be null at this point
            current = stack.pop();
            System.out.print(current.value + " ");
            
            // Visit the right subtree
            current = current.right;
        }
    }

    public static void main(String[] args) {
        /*
            Constructing the following binary tree:
                    4
                   / \
                  2   5
                 / \
                1   3
        */
        TreeNode root = new InorderTraversal().new TreeNode(4);
        root.left = new InorderTraversal().new TreeNode(2);
        root.right = new InorderTraversal().new TreeNode(5);
        root.left.left = new InorderTraversal().new TreeNode(1);
        root.left.right = new InorderTraversal().new TreeNode(3);

        System.out.println("Inorder traversal (non-recursive):");
        inorderIterative(root);
    }
}