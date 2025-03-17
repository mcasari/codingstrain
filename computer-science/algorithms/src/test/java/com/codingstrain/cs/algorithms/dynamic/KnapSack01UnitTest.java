package com.codingstrain.cs.algorithms.dynamic;

import org.junit.jupiter.api.Test;

class KnapSack01UnitTest {

    @Test
    public void knapSack01Test() throws Exception {
        int items[][] = { { 1, 5 }, { 2, 15 }, { 3, 6 } };
        int W = 10;
        int N = items.length;
        System.out.println(knapSackEvaluation(W, items, N));
    }

    private int knapSackEvaluation(int W, int items[][], int N) {

        if (N == 0 || W == 0) {
            return 0;
        }

        if (items[N - 1][1] > W) {
            return knapSackEvaluation(W, items, N - 1);
        } else {
            return maxInteger(items[N - 1][0] + knapSackEvaluation(W - items[N - 1][1], items, N - 1), knapSackEvaluation(W, items, N - 1));
        }
    }

    private int maxInteger(int a, int b) { 
        return (a > b) ? a : b; 
    }

}
