package com.codingstrain.cs.algorithms.trees;
import java.util.Stack;


public class InorderTraversal {
	
	class TreeNodeStructure {
	    int value;
	    TreeNodeStructure left;
	    TreeNodeStructure right;

	    TreeNodeStructure(int value) {
	        this.value = value;
	        this.left = null;
	        this.right = null;
	    }
	}

    public static void inorderIterative(TreeNodeStructure root) {
        Stack<TreeNodeStructure> stack = new Stack<>();
        TreeNodeStructure currentNode = root;

        while (currentNode != null || !stack.isEmpty()) {
            // Reach the leftmost node of the current node
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            }

            // Current must be null at this point
            currentNode = stack.pop();
            System.out.print(currentNode.value + " ");
            
            // Visit the right subtree
            currentNode = currentNode.right;
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
        TreeNodeStructure root = new InorderTraversal().new TreeNodeStructure(4);
        root.left = new InorderTraversal().new TreeNodeStructure(2);
        root.right = new InorderTraversal().new TreeNodeStructure(5);
        root.left.left = new InorderTraversal().new TreeNodeStructure(1);
        root.left.right = new InorderTraversal().new TreeNodeStructure(3);

        System.out.println("Inorder traversal (non-recursive):");
        inorderIterative(root);
    }
}