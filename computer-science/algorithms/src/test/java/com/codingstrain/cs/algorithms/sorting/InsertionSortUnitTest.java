package com.codingstrain.cs.algorithms.sorting;

import org.junit.jupiter.api.Test;

class InsertionSortUnitTest {

	@Test
	public void InsertionSortTest() throws Exception {
        int[] arrToBeSorted = {45, 56, 78, 109, 877, 57, 55, 57};
        sort(arrToBeSorted);
		for (int elem : arrToBeSorted) {
			System.out.println(elem + " ");
		}
	}

    public static void sort(int[] arr) {
        for (int currentIndex = 1; currentIndex < arr.length; currentIndex++) {
            int currentValue = arr[currentIndex];
            int position = currentIndex - 1;
            while (position >= 0 && arr[position] > currentValue) {
                arr[position + 1] = arr[position];
                position--;
            }
            arr[position + 1] = currentValue;
        }
    }    
}
