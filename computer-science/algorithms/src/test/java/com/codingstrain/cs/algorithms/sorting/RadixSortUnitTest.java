package com.codingstrain.cs.algorithms.sorting;

import org.junit.jupiter.api.Test;

class RadixSortUnitTest {

	@Test
	public void RadixSortTest() throws Exception {
        int[] arrToBeSorted = {55, 66, 88, 99, 677, 55, 25, 77};
        radixSortingArr(arrToBeSorted);
		for (int elem : arrToBeSorted) {
			System.out.println(elem + " ");
		}
	}

    private int getMaxVal(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }
        return max;
    }

    private void cSort(int[] arrToBeSorted, int exp) {
        int N = arrToBeSorted.length;
        int[] out = new int[N];
        int[] count = new int[10];
        
        for (int num : arrToBeSorted) {
            int dig = (num / exp) % 10;
            count[dig]++;
        }
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        for (int i = N - 1; i >= 0; i--) {
            int digit = (arrToBeSorted[i] / exp) % 10;
            out[count[digit] - 1] = arrToBeSorted[i];
            count[digit]--;
        }
        System.arraycopy(out, 0, arrToBeSorted, 0, N);
    }

    private void radixSortingArr(int[] arr) {
        int max = getMaxVal(arr);
        for (int exp = 1; max / exp > 0; exp *= 10) {
        	cSort(arr, exp);
        }
    }

}
