package com.codingstrain.cs.algorithms.sorting;

import org.junit.jupiter.api.Test;

class BubbleSortUnitTest {

	@Test
	public void BubbleSortTest() throws Exception {
        int[] arrToBeSorted = {55, 66, 88, 99, 677, 55, 25, 77};
        bubbleSortingAlg(arrToBeSorted);
		for (int elem : arrToBeSorted) {
			System.out.println(elem + " ");
		}
	}

    private void bubbleSortingAlg(int[] arrToBeSorted) {
        int n = arrToBeSorted.length;
        boolean swappedElems;      
        for (int i = 0; i < n - 1; i++) {
            swappedElems = false;          
            for (int j = 0; j < n - i - 1; j++) {
                if (arrToBeSorted[j] > arrToBeSorted[j + 1]) {
	                int temp = arrToBeSorted[j];
	                arrToBeSorted[j] = arrToBeSorted[j + 1];
	                arrToBeSorted[j + 1] = temp;
	                swappedElems = true;
                }
            }
            if (!swappedElems) {
            	break;            	
            }
        }
    }
    
}
