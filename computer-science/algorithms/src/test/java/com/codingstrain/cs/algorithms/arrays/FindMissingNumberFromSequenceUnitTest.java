package com.codingstrain.cs.algorithms.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FindMissingNumberFromSequenceUnitTest {

    @Test
    public void findMissingNumberBySumTest() throws Exception {
        int[] integerSet = { 5, 2, 4, 1 };
        int n = integerSet.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + integerSet[i];
        }
        assertEquals(3, ((n * (n + 1)) / 2 - sum));
        System.out.println(((n * (n + 1)) / 2 - sum));
    }

    @Test
    public void findMissingNumberByTempArrayTest() throws Exception {

        int integerSet[] = { 5, 2, 4, 1 };
        int n = integerSet.length;
        int temp[] = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            temp[i] = 0;
        }

        for (int i = 0; i < n; i++) {
            temp[integerSet[i] - 1] = 1;
        }

        int missingNumber = 0;
        for (int i = 0; i <= n; i++) {
            if (temp[i] == 0) {
                missingNumber = i + 1;
        }
    }
    assertEquals(3, missingNumber);
    System.out.println(missingNumber);
}


}
