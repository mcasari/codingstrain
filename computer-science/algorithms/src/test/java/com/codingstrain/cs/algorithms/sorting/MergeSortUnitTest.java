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

    // Function to perform merge sort
    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Recursively sort first and second halves
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            
            // Merge the sorted halves
            merge(array, left, mid, right);
        }
    }
    
    // Function to merge two subarrays
    public static void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++)
            leftArray[i] = array[left + i];
        for (int j = 0; j < n2; j++)
            rightArray[j] = array[mid + 1 + j];
        
        int i = 0, j = 0, k = left;
        
        // Merge the temporary arrays back into the main array
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        // Copy any remaining elements of leftArray
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }
        
        // Copy any remaining elements of rightArray
        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }



}
