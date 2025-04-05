package com.codingstrain.cs.algorithms.sorting;

import org.junit.jupiter.api.Test;

class MergeSortUnitTest {

	@Test
	public void mergeSortTest() throws Exception {
        int[] a = {130, 44, 77, 2, 9, 5, 77};       
        mergeSort(a, 0, a.length - 1);
        for (int elem : a) {
            System.out.println(elem + " ");
        }
	}

    private void mergeSort(int[] arrayToSort, int l, int r) {
        if (l < r) {
            int middle = l + (r - l) / 2;         
            mergeSort(arrayToSort, l, middle);
            mergeSort(arrayToSort, middle + 1, r);            
            merge(arrayToSort, l, middle, r);
        }
    }
    
    private void merge(int[] arrayToMerge, int l, int middle, int r) {
        int k1 = middle - l + 1;
        int k2 = r - middle;
        
        int[] leftArray = new int[k1];
        int[] rightArray = new int[k2];
        
        for (int i = 0; i < k1; i++)
            leftArray[i] = arrayToMerge[l + i];
        for (int j = 0; j < k2; j++)
            rightArray[j] = arrayToMerge[middle + 1 + j];
        
        int i = 0, j = 0, k = l;
        while (i < k1 && j < k2) {
            if (leftArray[i] <= rightArray[j]) {
            	arrayToMerge[k] = leftArray[i];
                i++;
            } else {
            	arrayToMerge[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        while (i < k1) {
        	arrayToMerge[k] = leftArray[i];
            i++;
            k++;
        }
        
        while (j < k2) {
        	arrayToMerge[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
