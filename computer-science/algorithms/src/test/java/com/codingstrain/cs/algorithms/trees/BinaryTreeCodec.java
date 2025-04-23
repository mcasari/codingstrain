package com.codingstrain.cs.algorithms.trees;

import java.util.*;

//Unique class name for serialization and deserialization of binary trees
public class BinaryTreeCodec {

 // Converts a binary tree into a string representation
 public String encode(TreeNode root) {
     StringBuilder output = new StringBuilder();
     encodeTree(root, output);
     return output.toString();
 }

 private void encodeTree(TreeNode current, StringBuilder output) {
     if (current == null) {
         output.append("#,");
         return;
     }
     output.append(current.val).append(",");
     encodeTree(current.left, output);
     encodeTree(current.right, output);
 }

 // Restores a binary tree from its string representation
 public TreeNode decode(String data) {
     Queue<String> nodeValues = new LinkedList<>(Arrays.asList(data.split(",")));
     return decodeTree(nodeValues);
 }

 private TreeNode decodeTree(Queue<String> nodeValues) {
     if (nodeValues.isEmpty()) return null;

     String value = nodeValues.poll();
     if ("#".equals(value)) return null;

     TreeNode node = new TreeNode(Integer.parseInt(value));
     node.left = decodeTree(nodeValues);
     node.right = decodeTree(nodeValues);

     return node;
 }
}

//Tree node structure
class TreeNode {
 int val;
 TreeNode left;
 TreeNode right;

 TreeNode(int value) {
     this.val = value;
 }
}