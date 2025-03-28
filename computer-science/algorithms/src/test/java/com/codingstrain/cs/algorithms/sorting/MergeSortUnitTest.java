package com.codingstrain.cs.algorithms.sorting;

import org.junit.jupiter.api.Test;

class MergeSortUnitTest {

	@Test
	public void mergeSortTest() throws Exception {
        int[] array = {38, 27, 43, 3, 9, 82, 10};       
        mergeSort(array, 0, array.length - 1);
        for (int elem : array) {
            System.out.println(elem + " ");
        }
	}

    private void mergeSort(int[] arrayToSort, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
           
            mergeSort(arrayToSort, left, mid);
            mergeSort(arrayToSort, mid + 1, right);
            
            merge(arrayToSort, left, mid, right);
        }
    }
    
    private void merge(int[] arrayToMerge, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        for (int i = 0; i < n1; i++)
            leftArray[i] = arrayToMerge[left + i];
        for (int j = 0; j < n2; j++)
            rightArray[j] = arrayToMerge[mid + 1 + j];
        
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
            	arrayToMerge[k] = leftArray[i];
                i++;
            } else {
            	arrayToMerge[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
        	arrayToMerge[k] = leftArray[i];
            i++;
            k++;
        }
        
        while (j < n2) {
        	arrayToMerge[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
