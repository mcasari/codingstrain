package com.codingstrain.cs.algorithms.strings;

import org.junit.jupiter.api.Test;

class KMPAlgorithmUnitTest {

	@Test
	public void kmpTest() throws Exception {
		String text = "ABABDABACDABABCABAB";
		String pattern = "ABABCABAB";
		search(text, pattern);
	}

	private void search(String textToSearch, String stringPattern) {
		int n = textToSearch.length();
		int m = stringPattern.length();
		int[] longestPrefixSuffix = computeLPSArray(stringPattern);

		int i = 0; // index for text
		int j = 0; // index for pattern

		while (i < n) {
			if (stringPattern.charAt(j) == textToSearch.charAt(i)) {
				i++;
				j++;
			}

			if (j == m) {
				System.out.println("Pattern found at index " + (i - j));
				j = longestPrefixSuffix[j - 1];
			} else if (i < n && stringPattern.charAt(j) != textToSearch.charAt(i)) {
				if (j != 0) {
					j = longestPrefixSuffix[j - 1];
				} else {
					i++;
				}
			}
		}
	}

	private int[] computeLPSArray(String stringPattern) {
		int m = stringPattern.length();
		int[] longestPrefixSuffix = new int[m];
		int len = 0; // length of the previous longest prefix suffix
		int i = 1;

		while (i < m) {
			if (stringPattern.charAt(i) == stringPattern.charAt(len)) {
				len++;
				longestPrefixSuffix[i] = len;
				i++;
			} else {
				if (len != 0) {
					len = longestPrefixSuffix[len - 1];
				} else {
					longestPrefixSuffix[i] = 0;
					i++;
				}
			}
		}
		return longestPrefixSuffix;
	}
}
