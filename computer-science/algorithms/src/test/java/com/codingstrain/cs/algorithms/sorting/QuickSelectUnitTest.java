package com.codingstrain.cs.algorithms.sorting;

import org.junit.jupiter.api.Test;

class QuickSelectUnitTest {

	@Test
	public void quickSelectTest() throws Exception {
		int[] array = { 8, 9, 4, 3, 23, 15 };
		int n = 3;
		System.out.println(quickSelect(array, 0, array.length - 1, n - 1));
	}

    private static int quickSelect(int[] array, int l, int r, int n) {
        if (l == r) {
            return array[l];
        }

        int pivot = partition(array, l, r);
        
        if (pivot == n) {
            return array[pivot];
        } else if (pivot > n) {
            return quickSelect(array, l, pivot - 1, n);
        } else {
            return quickSelect(array, pivot + 1, r, n);
        }
    }

    private static int partition(int[] array, int l, int r) {
        int pivot = array[r];
        int i = l;
        for (int j = l; j < r; j++) {
            if (array[j] <= pivot) {
                swapElements(array, i, j);
                i++;
            }
        }
        swapElements(array, i, r);
        return i;
    }

    private static void swapElements(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
