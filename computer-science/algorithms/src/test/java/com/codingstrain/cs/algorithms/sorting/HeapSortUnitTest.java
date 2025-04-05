package com.codingstrain.cs.algorithms.sorting;

import org.junit.jupiter.api.Test;

class HeapSortUnitTest {

	@Test
	public void HeapSortTest() throws Exception {
        int[] toSort = {13, 15, 21, 57, 67, 71};   
        sortArray(toSort);
        for (int elem : toSort) {
            System.out.println(elem + " ");
        }
	}

    private void sortArray(int[] toSort) {
        int len = toSort.length;
        
        for (int i = len/2 - 1; i >= 0; i--)
            heapifyArray(toSort, len, i);

        for (int i = len - 1; i >= 0; i--) {
            int temp = toSort[0];
            toSort[0] = toSort[i];
            toSort[i] = temp;
            heapifyArray(toSort, i, 0);
        }
    }

    void heapifyArray(int[] toSort, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        
        if (l < n && toSort[l] > toSort[largest])
            largest = l;
        
        if (r < n && toSort[r] > toSort[largest])
            largest = r;

        if (largest != i) {
            int swap = toSort[i];
            toSort[i] = toSort[largest];
            toSort[largest] = swap;
            heapifyArray(toSort, n, largest);
        }
    }
    
}
