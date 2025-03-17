package com.codingstrain.cs.algorithms.sorting;

import org.junit.jupiter.api.Test;

class QuickSortUnitTest {

    private void quickSort(int[] toSortArray, int left, int right) throws Exception {

        if (left < right) {
            int pivotPosition = step(toSortArray, left, right);

            quickSort(toSortArray, left, pivotPosition);
            quickSort(toSortArray, pivotPosition + 1, right);
        }

    }

    @Test
    public void quickSortTest() throws Exception {
        int[] toSortArray = { 5, 1, 2, 4, 3 };
        quickSort(toSortArray, 0, toSortArray.length - 1);
        System.out.println(toSortArray);
    }

    private int step(int[] toSortArray, int left, int right) throws Exception {
        int currentLow = left - 1;
        int pivotFinalPosition = right;
        //we choose the last element as pivot
        int pivot = toSortArray[right];
        for (int i = left; i <= right - 1; i++) {
            if (toSortArray[i] < pivot) {
                ++currentLow;
                pivotFinalPosition = currentLow;
                swap(toSortArray, i, currentLow);
        }
    }
    swap(toSortArray, ++currentLow, right);
    return pivotFinalPosition;
}

private void swap(int[] sortedSet, int i, int j) throws Exception {
    int temp = sortedSet[i];
    sortedSet[i] = sortedSet[j];
    sortedSet[j] = temp;
}

}
