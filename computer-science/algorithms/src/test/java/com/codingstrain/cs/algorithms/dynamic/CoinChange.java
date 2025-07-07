package com.codingstrain.cs.algorithms.dynamic;
public class CoinChange {

    public static int minimumCoins(int[] coins, int amountRequired) {
        int max = amountRequired + 1;
        int[] dbArr = new int[amountRequired + 1];

        for (int i = 0; i <= amountRequired; i++) {
            dbArr[i] = max;
        }
        dbArr[0] = 0;

        for (int coin : coins) {
            for (int i = coin; i <= amountRequired; i++) {
                dbArr[i] = Math.min(dbArr[i], dbArr[i - coin] + 1);
            }
        }

        return dbArr[amountRequired] > amountRequired ? -1 : dbArr[amountRequired];
    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;

        int result = minimumCoins(coins, amount);
        if (result != -1) {
            System.out.println("Minimum coins required: " + result);
        } else {
            System.out.println("Amount cannot be formed with given coins.");
        }
    }
}