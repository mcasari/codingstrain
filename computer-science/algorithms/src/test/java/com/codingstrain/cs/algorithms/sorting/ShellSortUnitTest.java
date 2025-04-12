package com.codingstrain.cs.algorithms.sorting;

import org.junit.jupiter.api.Test;

class ShellSortUnitTest {

	@Test
	public void ShellSortTest() throws Exception {
        int[] arrToBeSorted = {55, 66, 88, 99, 677, 55, 25, 77};
        sort(arrToBeSorted);
		for (int elem : arrToBeSorted) {
			System.out.println(elem + " ");
		}
	}

    private void sort(int[] values) {
        int n = values.length;
        for (int gap = initialGap(n); gap > 0; gap = nextGap(gap)) {
            applyGapSort(values, gap);
        }
    }

    private static int initialGap(int size) {
        return size / 2;
    }

    private int nextGap(int currentGap) {
        return currentGap / 2;
    }

    private void applyGapSort(int[] data, int gap) {
        for (int i = gap; i < data.length; i++) {
            int insertValue = data[i];
            int mover = i;

            while (mover >= gap && data[mover - gap] > insertValue) {
                data[mover] = data[mover - gap];
                mover -= gap;
            }

            data[mover] = insertValue;
        }
    }
}
