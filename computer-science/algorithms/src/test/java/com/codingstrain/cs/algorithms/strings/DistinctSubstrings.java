package com.codingstrain.cs.algorithms.strings;

import java.util.Arrays;

public class DistinctSubstrings {

    public static int countDistinctSubstrings(String inpustString) {
        inpustString += "$"; // Append terminal character
        int k = inpustString.length();
        int[] suffixArr = buildSuffixArr(inpustString);
        int[] lcp = buildLCPArray(inpustString, suffixArr);

        int total = 0;
        for (int i = 1; i < k; i++) {
            int suffixLength = k - 1 - suffixArr[i]; // Exclude terminal character
            total += suffixLength - lcp[i - 1];
        }
        return total;
    }

    private static int[] buildSuffixArr(String inputString) {
        int n = inputString.length();
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;

        int[] rankArr = new int[n];
        for (int i = 0; i < n; i++) rankArr[i] = inputString.charAt(i);

        int[] temp = new int[n];
        for (int len = 1; len < n; len *= 2) {
            final int l = len;
            Arrays.sort(order, (a, b) -> {
                if (rankArr[a] != rankArr[b]) return Integer.compare(rankArr[a], rankArr[b]);
                int ra = (a + l < n) ? rankArr[a + l] : -1;
                int rb = (b + l < n) ? rankArr[b + l] : -1;
                return Integer.compare(ra, rb);
            });

            temp[order[0]] = 0;
            for (int i = 1; i < n; i++) {
                temp[order[i]] = temp[order[i - 1]];
                if (rankArr[order[i]] != rankArr[order[i - 1]] ||
                    ((order[i] + l < n ? rankArr[order[i] + l] : -1) != 
                     (order[i - 1] + l < n ? rankArr[order[i - 1] + l] : -1))) {
                    temp[order[i]]++;
                }
            }
            System.arraycopy(temp, 0, rankArr, 0, n);
        }

        int[] sa = new int[n];
        for (int i = 0; i < n; i++) sa[i] = order[i];
        return sa;
    }

    private static int[] buildLCPArray(String inputString, int[] sa) {
        int n = inputString.length();
        int[] rankArr = new int[n];
        for (int i = 0; i < n; i++) rankArr[sa[i]] = i;

        int[] lcp = new int[n - 1];
        int h = 0;
        for (int i = 0; i < n; i++) {
            if (rankArr[i] > 0) {
                int j = sa[rankArr[i] - 1];
                while (i + h < n && j + h < n && inputString.charAt(i + h) == inputString.charAt(j + h)) {
                    h++;
                }
                lcp[rankArr[i] - 1] = h;
                if (h > 0) h--;
            }
        }
        return lcp;
    }

    public static void main(String[] args) {
        String input = "banana";
        int result = countDistinctSubstrings(input);
        System.out.println("Number of distinct substrings: " + result);
    }
}