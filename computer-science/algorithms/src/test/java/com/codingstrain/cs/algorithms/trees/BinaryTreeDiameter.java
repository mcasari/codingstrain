package com.codingstrain.cs.algorithms.trees;

public class BinaryTreeDiameter {
    private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        computeHeight(root);
        return diameter;
    }

    private int computeHeight(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = computeHeight(node.leftNode);
        int rightHeight = computeHeight(node.rightNode);

        diameter = Math.max(diameter, leftHeight + rightHeight);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        /*
            Example Tree:
                  1
                 / \
                2   3
               / \     
              4   5    

            Expected diameter: 3 (path: 4 -> 2 -> 1 -> 3)
        */
    	BinaryTreeDiameter binaryTreeDiameter = new BinaryTreeDiameter();
        TreeNode root = binaryTreeDiameter.new TreeNode(1);
        root.leftNode = binaryTreeDiameter.new TreeNode(2);
        root.rightNode = binaryTreeDiameter.new TreeNode(3);
        root.leftNode.leftNode = binaryTreeDiameter.new TreeNode(4);
        root.leftNode.rightNode = binaryTreeDiameter.new TreeNode(5);

        BinaryTreeDiameter tree = new BinaryTreeDiameter();
        int result = tree.diameterOfBinaryTree(root);
        System.out.println("Diameter of the tree is: " + result);
    }
    
    class TreeNode {
        int nodeVal;
        TreeNode leftNode;
        TreeNode rightNode;

        TreeNode(int nodeVal) {
            this.nodeVal = nodeVal;
        }
    }
}