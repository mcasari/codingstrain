package com.codingstrain.cs.algorithms.strings;

public class LongestRepeatingSubsequence {

    public static int findLongestRSLength(String inputString) {
        int N = inputString.length();
        int[][] dpArray = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (inputString.charAt(i - 1) == inputString.charAt(j - 1) && i != j) {
                    dpArray[i][j] = 1 + dpArray[i - 1][j - 1];
                } else {
                    dpArray[i][j] = Math.max(dpArray[i - 1][j], dpArray[i][j - 1]);
                }
            }
        }

        return dpArray[N][N];
    }

    public static void main(String[] args) {
        String inputString = "aab";
        int len = findLongestRSLength(inputString);
        System.out.println("Length of LRS: " + len);
    }
}