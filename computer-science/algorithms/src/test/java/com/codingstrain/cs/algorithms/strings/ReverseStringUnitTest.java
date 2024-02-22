package com.codingstrain.cs.algorithms.strings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReverseStringUnitTest {

    @Test
    public void reverseStringTest() throws Exception {

        String toReverse = "abcdef";
        char[] chArray = toReverse.toCharArray();

        int i = 0, j = chArray.length - 1;
        while (i < j) {
            char temp = chArray[i];
            chArray[i] = chArray[j];
            chArray[j] = temp;
            ++i;
            --j;
        }

        String reversed = String.valueOf(chArray);
        System.out.println(reversed);
        for (int n = 0, m = chArray.length - 1; n < chArray.length; n++, m--) {
            assertEquals(toReverse.toCharArray()[n], chArray[m]);
        }

    }

    @Test
    public void trivialReverseStringTest() throws Exception {

        String toReverse = "abcdef";
        char[] chArray = toReverse.toCharArray();
        char[] chArrayReversed = new char[chArray.length];

        for (int i = 0, j = chArray.length - 1; i < chArray.length; i++, j--) {
            chArrayReversed[i] = chArray[j];
        }

        System.out.println(chArrayReversed);
        for (int i = 0, j = chArray.length - 1; i < chArray.length; i++, j--) {
            assertEquals(chArrayReversed[i], chArray[j]);
        }

    }


}
