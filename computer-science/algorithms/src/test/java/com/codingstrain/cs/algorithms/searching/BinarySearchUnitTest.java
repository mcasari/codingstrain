package com.codingstrain.cs.algorithms.searching;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BinarySearchUnitTest {

    @Test
    public void binarySearchIterativeTest() throws Exception {
        int[] sortedSet = { 1, 2, 3, 4, 5 };
        int keyValue = 4;
        int i = 0, j = sortedSet.length - 1;
        int searchResult = -1;
        int middle = -1;
        while (i <= j) {
            middle = i + (j - i) / 2;

            if (keyValue == sortedSet[middle]) {
                searchResult = sortedSet[middle];
                break;
            }

            if (keyValue < sortedSet[middle]) {
                j = middle - 1;
            } else if (keyValue > sortedSet[middle]) {
                i = middle + 1;
            }
        }
        assertEquals(4, searchResult);
        System.out.println(searchResult);
    }

    private int binarySearchRecursive(int[] sortedSet, int i, int j, int keyValue) throws Exception {

        if (i <= j) {
            int middle = i + (j - i) / 2;

            if (keyValue == sortedSet[middle]) {
                return sortedSet[middle];
            }

            if (keyValue < sortedSet[middle]) {
                j = middle - 1;
            } else if (keyValue > sortedSet[middle]) {
                i = middle + 1;
            }

            return binarySearchRecursive(sortedSet, i, j, keyValue);
        }

        return -1;

    }

    @Test
    public void binarySearchRecursiveTest() throws Exception {

        int[] sortedSet = { 1, 2, 3, 4, 5 };
        int i = 0, j = sortedSet.length - 1;
        int keyValue = 4;
        int result = binarySearchRecursive(sortedSet, i, j, keyValue);

        assertEquals(4, result);
        System.out.println(result);
    }

}
