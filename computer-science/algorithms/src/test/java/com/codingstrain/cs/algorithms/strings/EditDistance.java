package com.codingstrain.cs.algorithms.strings;

public class EditDistance {

    public static int computeEditDistance(String word1, String word2) {
        int l1 = word1.length();
        int l2 = word2.length();
        
        
        int[][] dpArr = new int[l1 + 1][l2 + 1];

        for (int i = 0; i <= l1; i++) {
            dpArr[i][0] = i;
        }

        for (int j = 0; j <= l2; j++) {
            dpArr[0][j] = j;
        }

        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dpArr[i][j] = dpArr[i - 1][j - 1];
                } else {
                    int insert = dpArr[i][j - 1];
                    int delete = dpArr[i - 1][j];
                    int replace = dpArr[i - 1][j - 1];
                    dpArr[i][j] = 1 + Math.min(insert, Math.min(delete, replace));
                }
            }
        }

        return dpArr[l1][l2];
    }

    public static void main(String[] args) {
        String word1 = "kitten";
        String word2 = "sitting";
        int distance = computeEditDistance(word1, word2);
        System.out.println("Edit distance between \"" + word1 + "\" and \"" + word2 + "\" is: " + distance);
    }
}