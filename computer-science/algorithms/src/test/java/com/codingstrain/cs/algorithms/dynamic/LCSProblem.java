package com.codingstrain.cs.algorithms.dynamic;

public class LCSProblem {

    public static String longestCommonSubseq(String input1, String input2) {
        int m = input1.length();
        int n = input2.length();
        int[][] dbArr = new int[m + 1][n + 1];

        // Build the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (input1.charAt(i - 1) == input2.charAt(j - 1)) {
                    dbArr[i][j] = dbArr[i - 1][j - 1] + 1;
                } else {
                    dbArr[i][j] = Math.max(dbArr[i - 1][j], dbArr[i][j - 1]);
                }
            }
        }

        // Reconstruct the LCS from the DP table
        StringBuilder lcsString = new StringBuilder();
        int i = m, j = n;

        while (i > 0 && j > 0) {
            if (input1.charAt(i - 1) == input2.charAt(j - 1)) {
                lcsString.append(input1.charAt(i - 1));
                i--;
                j--;
            } else if (dbArr[i - 1][j] > dbArr[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcsString.reverse().toString();
    }

    public static void main(String[] args) {
        String s1 = "ACDBE";
        String s2 = "ABCDE";
        String result = longestCommonSubseq(s1, s2);
        System.out.println("LCS: " + result);
    }
}