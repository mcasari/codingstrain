package com.codingstrain.cs.algorithms.sorting;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class BucketSortUnitTest {

	@Test
	public void ShellSortTest() throws Exception {
        float[] input = {0.23f, 0.15f, 0.78f, 0.91f, 0.45f, 0.39f};
        bucketSort(input);
		for (float elem : input) {
			System.out.println(elem + " ");
		}
	}

    private void bucketSort(float[] arr) {
        if (arr.length <= 0)
            return;

        int n = arr.length;
        ArrayList<Float>[] buckets = new ArrayList[n];

        for (int i = 0; i < n; i++)
            buckets[i] = new ArrayList<>();

        for (float value : arr) {
            int index = (int)(value * n);
            buckets[index].add(value);
        }

        for (ArrayList<Float> bucket : buckets)
            Collections.sort(bucket);

        int index = 0;
        for (ArrayList<Float> bucket : buckets) {
            for (float value : bucket) {
                arr[index++] = value;
            }
        }
    }

}
