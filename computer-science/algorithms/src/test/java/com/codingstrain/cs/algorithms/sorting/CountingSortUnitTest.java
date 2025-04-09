package com.codingstrain.cs.algorithms.sorting;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class CountingSortUnitTest {

	@Test
	public void CountingSortTest() throws Exception {
		int[] arrToBeSorted = { 13, 15, 21, 57, 67, 71 };
		cSort(arrToBeSorted);
		for (int elem : arrToBeSorted) {
			System.out.println(elem + " ");
		}
	}

	public static void cSort(int[] arrToBeSorted) {
		if (arrToBeSorted.length == 0)
			return;
		int max = Arrays.stream(arrToBeSorted).max().getAsInt();
		int[] countArray = new int[max + 1];

		for (int num : arrToBeSorted) {
			countArray[num]++;
		}

		for (int i = 1; i < countArray.length; i++) {
			countArray[i] += countArray[i - 1];
		}

		int[] output = new int[arrToBeSorted.length];
		for (int i = arrToBeSorted.length - 1; i >= 0; i--) {
			output[--countArray[arrToBeSorted[i]]] = arrToBeSorted[i];
		}
		System.arraycopy(output, 0, arrToBeSorted, 0, arrToBeSorted.length);
	}

}
