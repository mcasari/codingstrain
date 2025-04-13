package com.codingstrain.cs.algorithms.trees;

public class LowestCommonAncBinTree {

    public TreeNode lCA(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode leftLCA = lCA(root.leftNode, p, q);
        TreeNode rightLCA = lCA(root.rightNode, p, q);

        if (leftLCA != null && rightLCA != null) {
            return root;
        }

        return (leftLCA != null) ? leftLCA : rightLCA;
    }

    public static void main(String[] args) {
        LowestCommonAncBinTree tree = new LowestCommonAncBinTree();

        LowestCommonAncBinTree.TreeNode root = tree.new TreeNode(3);
        root.leftNode = tree.new TreeNode(5);
        root.rightNode = tree.new TreeNode(1);
        root.leftNode.leftNode = tree.new TreeNode(6);
        root.leftNode.rightNode = tree.new TreeNode(2);
        root.rightNode.leftNode = tree.new TreeNode(0);
        root.rightNode.rightNode = tree.new TreeNode(8);
        root.leftNode.rightNode.leftNode = tree.new TreeNode(7);
        root.leftNode.rightNode.rightNode = tree.new TreeNode(4);

        TreeNode p = root.leftNode; // Node 5
        TreeNode q = root.rightNode; // Node 1

        TreeNode lca = tree.lCA(root, p, q);
        System.out.println("Lowest Common Ancestor of " + p.nodeVal + " and " + q.nodeVal + " is: " + lca.nodeVal);
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