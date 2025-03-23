package com.codingstrain.cs.algorithms.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

class NthSmallestElementUnsortedArrayTestUnitTest {

	@Test
	public void findNthSmallestElementTest() throws Exception {
		int[] array = { 8, 9, 4, 3, 23, 15 };
		int n = 3;
		Arrays.sort(array);
		assertEquals(8, array[n - 1]);
		System.out.println("Nth smallest element: " + array[n - 1]);
	}

	@Test
	public void findNthSmallestElementMinHeapTest() throws Exception {
		int[] array = { 8, 9, 4, 3, 23, 15 };
		int n = 3;
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();
		for (int num : array) {
			minHeap.add(num);
		}
		for (int i = 1; i < n; i++) {
			minHeap.poll();
		}
		int result = minHeap.poll();
		assertEquals(8, result);
		System.out.println("Nth smallest element: " + result);
	}

	private int part(int[] array, int l, int r) {
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

	private int quickSel(int[] array, int l, int r, int n) {
		if (l == r) {
			return array[l];
		}

		int pivotIndex = part(array, l, r);

		if (n - 1 == pivotIndex) {
			return array[pivotIndex];
		} else if (n - 1 < pivotIndex) {
			return quickSel(array, l, pivotIndex - 1, n);
		} else {
			return quickSel(array, pivotIndex + 1, r, n);
		}
	}

	private void swapElements(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	@Test
	public void findNthSmallestElementQuickSelectTest() throws Exception {
		int[] array = { 8, 9, 4, 3, 23, 15 };
		int n = 3;
		int result = quickSel(array, 0, array.length - 1, n);
		assertEquals(8, result);
		System.out.println("Nth smallest element: " + result);
	}

}
